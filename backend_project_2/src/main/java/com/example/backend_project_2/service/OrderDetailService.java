// package com.example.backend_project_2.service;


// import com.example.backend_project_2.entity.Cart;
// import com.example.backend_project_2.entity.OrderDetail;
// import com.example.backend_project_2.entity.Product;
// import com.example.backend_project_2.repository.OrderDetailRepository;
// import com.example.backend_project_2.repository.CartRepository;
// import com.example.backend_project_2.repository.ProductRepository;
// import org.springframework.beans.factory.annotation.Autowired;
// import org.springframework.stereotype.Service;

// import java.util.List;

// @Service
// public class OrderDetailService {

//     @Autowired
//     private OrderDetailRepository orderDetailRepository;

//     @Autowired
//     private CartRepository cartRepository;

//     @Autowired
//     private ProductRepository productRepository;

//     // Get all OrderDetails
//     public List<OrderDetail> getAllOrderDetails() {
//         return orderDetailRepository.findAll();
//     }

//     // Get OrderDetail by ID
//     public OrderDetail getOrderDetailById(Long id) {
//         return orderDetailRepository.findById(id)
//                 .orElseThrow(() -> new RuntimeException("OrderDetail not found with id: " + id));
//     }

//     // Create a new OrderDetail
//     public OrderDetail createOrderDetail(Long cartId, Long productId, Integer quantity) {
//         Cart cart = cartRepository.findById(cartId)
//                 .orElseThrow(() -> new RuntimeException("Cart not found with id: " + cartId));
//         Product product = productRepository.findById(productId)
//                 .orElseThrow(() -> new RuntimeException("Product not found with id: " + productId));

//         OrderDetail orderDetail = new OrderDetail();
//         orderDetail.setCart(cart);
//         orderDetail.setProduct(product);
//         orderDetail.setProductPrice(product.getPrice());
//         orderDetail.setProductQuantity(quantity);

//         return orderDetailRepository.save(orderDetail);
//     }

//     // Update an existing OrderDetail
//     public OrderDetail updateOrderDetail(Long id, Long productId, Integer quantity) {
//         OrderDetail orderDetail = getOrderDetailById(id);
//         Product product = productRepository.findById(productId)
//                 .orElseThrow(() -> new RuntimeException("Product not found with id: " + productId));

//         orderDetail.setProduct(product);
//         orderDetail.setProductPrice(product.getPrice());
//         orderDetail.setProductQuantity(quantity);

//         return orderDetailRepository.save(orderDetail);
//     }

//     // Delete an OrderDetail
//     public void deleteOrderDetail(Long id) {
//         OrderDetail orderDetail = getOrderDetailById(id);
//         orderDetailRepository.delete(orderDetail);
//     }

//     // Get all OrderDetails by Cart ID
//     public List<OrderDetail> getOrderDetailsByCartId(Long cartId) {
//         Cart cart = cartRepository.findById(cartId)
//                 .orElseThrow(() -> new RuntimeException("Cart not found with id: " + cartId));
//         return cart.getOrderDetails();
//     }
// }

// package com.example.backend_project_2.service;

// import com.example.backend_project_2.entity.Cart;
// import com.example.backend_project_2.entity.OrderDetail;
// import com.example.backend_project_2.entity.Product;
// import com.example.backend_project_2.repository.OrderDetailRepository;
// import com.example.backend_project_2.repository.CartRepository;
// import com.example.backend_project_2.repository.ProductRepository;
// import org.springframework.beans.factory.annotation.Autowired;
// import org.springframework.stereotype.Service;
// import org.springframework.kafka.core.KafkaTemplate;

// import java.util.List;

// @Service
// public class OrderDetailService {

//     @Autowired
//     private OrderDetailRepository orderDetailRepository;

//     @Autowired
//     private CartRepository cartRepository;

//     @Autowired
//     private ProductRepository productRepository;

//     @Autowired
//     private KafkaProducerService kafkaProducerService;

//     public List<OrderDetail> getAllOrderDetails() {
//         return orderDetailRepository.findAll();
//     }

//     public OrderDetail getOrderDetailById(Long id) {
//         return orderDetailRepository.findById(id)
//                 .orElseThrow(() -> new RuntimeException("OrderDetail not found with id: " + id));
//     }

//     public OrderDetail createOrderDetail(Long cartId, Long productId, Integer quantity) {
//         Cart cart = cartRepository.findById(cartId)
//                 .orElseThrow(() -> new RuntimeException("Cart not found with id: " + cartId));
//         Product product = productRepository.findById(productId)
//                 .orElseThrow(() -> new RuntimeException("Product not found with id: " + productId));

//         OrderDetail orderDetail = new OrderDetail();
//         orderDetail.setCart(cart);
//         orderDetail.setProduct(product);
//         orderDetail.setProductPrice(product.getPrice());
//         orderDetail.setProductQuantity(quantity);

//         OrderDetail savedOrderDetail = orderDetailRepository.save(orderDetail);

//         // Send Kafka event
//         kafkaProducerService.sendMessage("order-details", "Created OrderDetail: " + savedOrderDetail.getOrderId());

//         return savedOrderDetail;
//     }

//     public OrderDetail updateOrderDetail(Long id, Long productId, Integer quantity) {
//         OrderDetail orderDetail = getOrderDetailById(id);
//         Product product = productRepository.findById(productId)
//                 .orElseThrow(() -> new RuntimeException("Product not found with id: " + productId));

//         orderDetail.setProduct(product);
//         orderDetail.setProductPrice(product.getPrice());
//         orderDetail.setProductQuantity(quantity);

//         OrderDetail updatedOrderDetail = orderDetailRepository.save(orderDetail);

//         // Send Kafka event
//         kafkaProducerService.sendMessage("order-details", "Updated OrderDetail: " + updatedOrderDetail.getOrderId());

//         return updatedOrderDetail;
//     }

//     public void deleteOrderDetail(Long id) {
//         OrderDetail orderDetail = getOrderDetailById(id);
//         orderDetailRepository.delete(orderDetail);

//         // Send Kafka event
//         kafkaProducerService.sendMessage("order-details", "Deleted OrderDetail: " + id);
//     }

//     public List<OrderDetail> getOrderDetailsByCartId(Long cartId) {
//         Cart cart = cartRepository.findById(cartId)
//                 .orElseThrow(() -> new RuntimeException("Cart not found with id: " + cartId));
//         return cart.getOrderDetails();
//     }
// }


package com.example.backend_project_2.service;

import com.example.backend_project_2.entity.Cart;
import com.example.backend_project_2.entity.OrderDetail;
import com.example.backend_project_2.entity.Product;
import com.example.backend_project_2.kafka.KafkaProducerService;
import com.example.backend_project_2.repository.OrderDetailRepository;
import com.example.backend_project_2.repository.CartRepository;
import com.example.backend_project_2.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class OrderDetailService {

    @Autowired
    private OrderDetailRepository orderDetailRepository;

    @Autowired
    private CartRepository cartRepository;

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private KafkaProducerService kafkaProducerService;

    // Get all order details
    public List<OrderDetail> getAllOrderDetails() {
        return orderDetailRepository.findAll();
    }

    // Get order detail by ID
    public OrderDetail getOrderDetailById(Long id) {
        return orderDetailRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("OrderDetail not found with id: " + id));
    }

    // Get all order details by Cart ID
    public List<OrderDetail> getOrderDetailsByCartId(Long cartId) {
        Cart cart = cartRepository.findById(cartId)
                .orElseThrow(() -> new RuntimeException("Cart not found with id: " + cartId));
        return cart.getOrderDetails();
    }

    // Create a new order detail
    public OrderDetail createOrderDetail(Long cartId, Long productId, Integer quantity) {
        Cart cart = cartRepository.findById(cartId)
                .orElseThrow(() -> new RuntimeException("Cart not found with id: " + cartId));
        Product product = productRepository.findById(productId)
                .orElseThrow(() -> new RuntimeException("Product not found with id: " + productId));

        OrderDetail orderDetail = new OrderDetail();
        orderDetail.setCart(cart);
        orderDetail.setProduct(product);
        orderDetail.setProductPrice(product.getPrice());
        orderDetail.setProductQuantity(quantity);

        OrderDetail savedOrderDetail = orderDetailRepository.save(orderDetail);

        // Send Kafka event
        kafkaProducerService.sendMessage("order-details", "Created OrderDetail: " + savedOrderDetail.getOrderId());

        return savedOrderDetail;
    }

    // Update an existing order detail
    public OrderDetail updateOrderDetail(Long id, Long productId, Integer quantity) {
        OrderDetail orderDetail = getOrderDetailById(id);
        Product product = productRepository.findById(productId)
                .orElseThrow(() -> new RuntimeException("Product not found with id: " + productId));

        orderDetail.setProduct(product);
        orderDetail.setProductPrice(product.getPrice());
        orderDetail.setProductQuantity(quantity);

        OrderDetail updatedOrderDetail = orderDetailRepository.save(orderDetail);

        // Send Kafka event
        kafkaProducerService.sendMessage("order-details", "Updated OrderDetail: " + updatedOrderDetail.getOrderId());

        return updatedOrderDetail;
    }

    // Delete an order detail
    public void deleteOrderDetail(Long id) {
        OrderDetail orderDetail = getOrderDetailById(id);
        orderDetailRepository.delete(orderDetail);

        // Send Kafka event
        kafkaProducerService.sendMessage("order-details", "Deleted OrderDetail: " + id);
    }
}


