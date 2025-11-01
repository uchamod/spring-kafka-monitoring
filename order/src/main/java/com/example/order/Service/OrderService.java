package com.example.order.Service;
import com.example.order.DTO.*;
import com.example.order.Feign.OrderFeignClient;

import com.example.order.Model.Order;
import com.example.order.Model.OrderProductModel;
import com.example.order.Repostory.OrderRepo;
import com.example.order.Service.Kproducer.MailProducerEvent;

import com.uchamod.commonmodules.Models.Cart;
import com.uchamod.commonmodules.Models.CartProduct;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@RequiredArgsConstructor
@org.springframework.stereotype.Service
@Transactional
public class OrderService {

    private final OrderRepo orderRepo;
    private final OrderFeignClient orderFeignClient;

    private final MailProducerEvent mailProducerEvent;

    //place new order
    public ResponseEntity<Order> placeOrder(UUID uuid) {
        try{
            if(uuid == null){
                return ResponseEntity.badRequest().build();
            }
            //get cart data
            ResponseEntity<Cart> cartDTOEntity= orderFeignClient.getCartByUserId(uuid);
            Cart cartDTO=cartDTOEntity.getBody();

            //set order data
            Order order=new Order();
            order.setCustomerId(cartDTO.getCustomerId());
            order.setTotalAmount(cartDTO.getTotalAmount());
            List<OrderProductModel> orderProductModelList=new ArrayList<>();
            for(CartProduct cartProductDTO : cartDTO.getCartProductList()){
                orderProductModelList.add(new OrderProductModel(cartProductDTO.getProductId(),
                        cartProductDTO.getSellerId(),
                        cartProductDTO.getProductCount(),
                        cartProductDTO.getProductItemPrice()));
            }
             order.setOrderProductModelList(orderProductModelList);
             orderRepo.save(order);

             //send acknowledgement mail to seller
            //use kafka
            mailProducerEvent.sendInventoryEvent(order);
            return ResponseEntity.ok(order);
        }catch (Exception e){
            System.out.println("error while creating order "+e.getMessage());
            return ResponseEntity.internalServerError().build();
        }
    }
    //get all orders (Admin)
    public ResponseEntity<List<Order>> getAllOrders() {
        try{
            List<Order> orders=  orderRepo.findAll();
            return ResponseEntity.ok(orders);
        }catch (Exception e){
            System.out.println("cannot get all orders"+e.getMessage());
            return ResponseEntity.internalServerError().build();
        }
    }
    //get orders by seller id
    public ResponseEntity<List<SellerDTO>> getOrdersBySellerId(UUID uuid,String role) {
        try{
            System.out.println(role);
            if(uuid == null || !role.equals("SELLER")){
                System.out.println("role or id is empty");
                return ResponseEntity.badRequest().build();
            }
           List<SellerDTO> sellerDTOList= orderRepo.findSellerOrdersBySellerId(uuid);
            if(sellerDTOList.isEmpty()){
                return ResponseEntity.notFound().build();
            }

            return ResponseEntity.ok(sellerDTOList);
        }catch (Exception e){
            System.out.println("cannot get orders by seller id");
            return ResponseEntity.internalServerError().build();
        }
    }
    //get orders by customer id
    public ResponseEntity<List<Order>> getOrdersByCustomerId(UUID uuid, String role) {
        try{
            if(uuid == null || !role.equals("CUSTOMER")){
                return ResponseEntity.badRequest().build();
            }
           List<Order> orders= orderRepo.findOrdersByCustomerId(uuid);
            if(orders.isEmpty()){
                return ResponseEntity.notFound().build();
            }
            return ResponseEntity.ok(orders);
        }catch (Exception e){
            System.out.println("cannot get orders by seller id");
            return ResponseEntity.internalServerError().build();
        }
    }
//update order state to paid
    public ResponseEntity<String> purcheForOrder(UUID orderId,String status) {
        try{
            if(orderId == null || status.isEmpty()){
                return ResponseEntity.badRequest().build();
            }
           Optional<Order> order= orderRepo.findById(orderId);
            if(order.isEmpty()){
                return ResponseEntity.notFound().build();
            }
            order.get().setOrderStatus(status);
            //when paid clear from cart
            if(status.equals("PAID")){
                ResponseEntity<String> result= orderFeignClient.checkoutFromCart(order.get().getCustomerId());
                if(result.getStatusCode().isError()){
                    return ResponseEntity.internalServerError().build();
                }
                System.out.println(result.getBody());
            }
            orderRepo.save(order.get());
                mailProducerEvent.sendInventoryEvent(order.get());

            return ResponseEntity.ok("status updated "+status);
        }catch (Exception e){
            System.out.println("cannot get orders by seller id");
            return ResponseEntity.internalServerError().build();
        }
    }
}
