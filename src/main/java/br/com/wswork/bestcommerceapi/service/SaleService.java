    package br.com.wswork.bestcommerceapi.service;

    import br.com.wswork.bestcommerceapi.exception.SaleNotFoundException;
    import br.com.wswork.bestcommerceapi.model.Sale;
    import br.com.wswork.bestcommerceapi.repository.SaleRepository;
    import org.springframework.beans.factory.annotation.Autowired;
    import org.springframework.stereotype.Service;

    import java.time.LocalDateTime;
    import java.util.List;
    import java.util.Optional;

    @Service
    public class SaleService {

        private final SaleRepository saleRepository;

        @Autowired
        public SaleService (SaleRepository saleRepository) {
            this.saleRepository = saleRepository;
        }

        public List<Sale> getAllSales() {

            return saleRepository.findAll();
        }
        public List<Sale> getAllSalesByCustomer(Long id) {

            return saleRepository.findAllSalesByCustomer(id);
        }

        public Sale getSaleById(Long id) {

            Optional<Sale> sale = saleRepository.findById(id);

            return sale.orElseThrow(() -> new SaleNotFoundException("Sale with name " + id + " not found."));
        }

        public Sale addSale(Sale newSale) {

            saleRepository.save(newSale);

            return newSale;
        }

        public Sale modifySale(Sale modifiedSale, Long id) {

            Optional<Sale> sale = saleRepository.findById(id);

            if (sale.isEmpty())
                throw new SaleNotFoundException("Sale with id " + id + " not found.");

            sale.get().setId(modifiedSale.getId());
            sale.get().setCustomer(modifiedSale.getCustomer());
            sale.get().setStore(modifiedSale.getStore());
            sale.get().setProducts(modifiedSale.getProducts());
            sale.get().setSaleDate(modifiedSale.getSaleDate());

            saleRepository.save(sale.get());

            return sale.get();
        }

        public void deleteSaleById(Long id) {

            Optional<Sale> sale = saleRepository.findById(id);

            if (sale.isEmpty())
                throw new SaleNotFoundException("Sale with id " + id + " not found.");

            saleRepository.deleteById(id);
        }
    }