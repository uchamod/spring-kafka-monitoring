# Spring Kafka Monitoring

A Spring Boot application designed to demonstrate asynchronous data communication using Apache Kafka, with complete monitoring capabilities using Prometheus and Grafana.

## Overview

This project shows how to build a modern event-driven application that:
- Sends and receives messages asynchronously using Kafka
- Tracks application performance and health metrics
- Visualizes data flows and system behavior in real-time

## Key Features

### Kafka Integration
- **Message Producers**: Send data to Kafka topics
- **Message Consumers**: Process incoming messages from topics
- **Asynchronous Communication**: Non-blocking data exchange between services

### Monitoring Stack
- **Prometheus**: Collects and stores metrics from the application
- **Grafana**: Creates visual dashboards to display metrics and alerts

## Technology Stack

- **Spring Boot**: Main application framework
- **Apache Kafka**: Message broker for asynchronous communication
- **Prometheus**: Metrics collection and storage
- **Grafana**: Visualization and monitoring dashboards
- **Java**: Programming language

## What You'll Need

- Java Development Kit (JDK) 17 or higher
- Apache Kafka (or Docker to run Kafka)
- Maven or Gradle for building the project
- Docker and Docker Compose (recommended for easy setup)

## Project Structure

```
spring-kafka-monitoring/
├── src/
│   ├── main/
│   │   ├── java/          # Application source code
│   │   └── resources/     # Configuration files
│   └── test/              # Test files
├── docker/                # Docker configuration files
├── grafana/              # Grafana dashboard configurations
└── prometheus/           # Prometheus configuration
```

## Getting Started

### Prerequisites
1. Install Java 17+
2. Install Docker and Docker Compose
3. Clone this repository

### Running the Application

1. **Start Kafka and monitoring tools**:
   ```bash
   docker-compose up -d
   ```

2. **Build the application**:
   ```bash
   ./mvnw clean install
   ```

3. **Run the Spring Boot application**:
   ```bash
   ./mvnw spring-boot:run
   ```

### Accessing the Services

- **Application**: http://localhost:8080
- **Prometheus**: http://localhost:9090
- **Grafana**: http://localhost:3000
- **Kafka UI** (if included): http://localhost:8081

## How It Works

1. **Data Production**: The application sends messages to Kafka topics
2. **Data Consumption**: Consumers listen to topics and process messages
3. **Metrics Collection**: Prometheus scrapes metrics from the application
4. **Visualization**: Grafana displays metrics in customizable dashboards

## Monitoring Metrics

The application exposes various metrics including:
- Message production/consumption rates
- Processing times and latency
- Error rates and system health
- JVM and application-specific metrics

## Configuration

Main configuration files:
- `application.yml`: Spring Boot and Kafka settings
- `prometheus.yml`: Prometheus scraping configuration
- `docker-compose.yml`: Container orchestration setup

## Development

### Adding New Kafka Topics
1. Define topic configuration in application properties
2. Create producer/consumer classes
3. Update metrics exposure if needed

### Customizing Dashboards
1. Access Grafana at http://localhost:3000
2. Import or create new dashboards
3. Configure data sources and panels

## Contributing

Contributions are welcome! Please feel free to submit pull requests or open issues for bugs and feature requests.

## License

[Add your license here]

## Contact

[Add your contact information]

---

**Repository**: https://github.com/uchamod/spring-kafka-monitoring
