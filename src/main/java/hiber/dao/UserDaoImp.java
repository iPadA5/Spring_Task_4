package hiber.dao;

import hiber.model.Car;
import hiber.model.User;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import javax.persistence.Query;
import javax.persistence.TypedQuery;
import java.util.List;

@Repository
public class UserDaoImp implements UserDao {

    @Autowired
    private SessionFactory sessionFactory;

    @Override
    public void add(User user) {
        if (!isUserExist(user.getEmail())) {
            sessionFactory.getCurrentSession().save(user);
        } else {
            System.out.println("email: " + user.getEmail() + " already exists");
        }
    }

    @Override
    @SuppressWarnings("unchecked")
    public List<User> listUsers() {
        TypedQuery<User> query = sessionFactory.getCurrentSession().createQuery("from User");
        return query.getResultList();
    }

    @Override
    public List<User> getUserByCar(String model, int series) {
        Car car;
        Query carQuery = sessionFactory.getCurrentSession().createQuery("from Car where model = :model and series = :series");
        carQuery.setParameter("model", model);
        carQuery.setParameter("series", series);
        carQuery.setMaxResults(1);
        car = (Car) carQuery.getSingleResult();
        Query userQuery = sessionFactory.getCurrentSession().createQuery("from User where car = :car");
        userQuery.setParameter("car", car);
        List<User> users = userQuery.getResultList();
        return users;
    }

    public boolean isUserExist(String email) {
        Query query = sessionFactory.getCurrentSession().createQuery("from User where email = :email");
        query.setParameter("email", email);
        return !query.getResultList().isEmpty();
    }
}
