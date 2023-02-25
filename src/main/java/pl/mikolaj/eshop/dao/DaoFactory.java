package pl.mikolaj.eshop.dao;

import pl.mikolaj.eshop.dao.impl.DefaultProductDao;
import pl.mikolaj.eshop.dao.impl.DefaultProductReferenceDao;

public class DaoFactory {
    private static ProductDao productDao;
    private static ProductReferenceDao productReferenceDao;

    public static synchronized ProductDao getProductDao() {
        if(productDao == null) {
            productReferenceDao = new DefaultProductReferenceDao();
            productDao = new DefaultProductDao(productReferenceDao);
            productReferenceDao.setProductDao(productDao);
        }
        return productDao;
    }
}
