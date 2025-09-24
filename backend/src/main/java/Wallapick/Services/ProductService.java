public int updateProduct(Product product, String token) {

        try {

            User user = jwtUser.getUser(token);
            Product existingProduct = productRepository.findById(product.getId()).orElse(null);

            if (existingProduct != null && existingProduct.getSeller().getId() == user.getId() && existingProduct.isForSale()) {

                existingProduct.setName(product.getName());
                existingProduct.setDescription(product.getDescription());
                existingProduct.setPrice(product.getPrice());
                existingProduct.setCategory(product.getCategory());
                existingProduct.setStatus((product.getStatus()));
                productRepository.save(existingProduct);

                // Copy the updated fields to the product (object passed by reference)
                product.setName(existingProduct.getName());
                product.setDescription(existingProduct.getDescription());
                product.setPrice(existingProduct.getPrice());
                product.setCategory(existingProduct.getCategory());
                product.setStatus(existingProduct.getStatus());
                product.setSeller(existingProduct.getSeller());

                return 1; // Success
            }

            return 0; // Not authorized or product not found

        } catch (Exception e) {
            return -1;
        }
    }
