package com.likesmeverywell.Webshop.service;

import com.likesmeverywell.Webshop.model.Checkout;
import com.likesmeverywell.Webshop.model.Product;
import com.likesmeverywell.Webshop.model.User;
import com.likesmeverywell.Webshop.repository.CheckoutRepository;
import com.likesmeverywell.Webshop.repository.ProductRepository;
import com.likesmeverywell.Webshop.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class CheckoutService {

    private final CheckoutRepository checkoutRepository;
    private final UserRepository userRepository;
    private final ProductRepository productRepository;

    public Checkout createCheckout(Checkout checkout) {

        Optional<User> userOptional = userRepository.findById(checkout.getUser().getId());
        if (userOptional.isEmpty()) {
            throw new RuntimeException("User not found");
        }
        User user = userOptional.get();

        checkout.setUser(user);

        double totalAmount = calculateTotalAmount(checkout);
        checkout.setTotalAmount(totalAmount);

        return checkoutRepository.save(checkout);
    }


    public Optional<Checkout> getCheckoutById(Long id) {
        return checkoutRepository.findById(id);
    }


    public Checkout updateCheckout(Long id, Checkout updatedCheckout) {
        Optional<Checkout> existingCheckoutOptional = checkoutRepository.findById(id);
        if (existingCheckoutOptional.isEmpty()) {
            throw new RuntimeException("Checkout not found");
        }

        Checkout existingCheckout = existingCheckoutOptional.get();

        existingCheckout.setProductIds(updatedCheckout.getProductIds());
        existingCheckout.setShippingAddress(updatedCheckout.getShippingAddress());

        double totalAmount = calculateTotalAmount(existingCheckout);
        existingCheckout.setTotalAmount(totalAmount);

        return checkoutRepository.save(existingCheckout);
    }


    public void deleteCheckout(Long id) {
        if (!checkoutRepository.existsById(id)) {
            throw new RuntimeException("Checkout not found");
        }
        checkoutRepository.deleteById(id);
    }

    private double calculateTotalAmount(Checkout checkout) {
        double totalAmount = 0.0;
        List<Long> productIds = checkout.getProductIds();

        for (Long productId : productIds) {
            Optional<Product> productOptional = productRepository.findById(productId);
            if (productOptional.isEmpty()) {
                throw new RuntimeException("Product with ID " + productId + " not found");
            }
            Product product = productOptional.get();
            totalAmount += product.getPrice();
        }


        totalAmount = applyDiscounts(totalAmount, checkout.getUser());

        return totalAmount;
    }


    private double applyDiscounts(double totalAmount, User user) {
        if (user.getRole() == user.getRole()) {
            return totalAmount * 0.9; // 10% discount for VIP users
        }
        return totalAmount;
    }
}
