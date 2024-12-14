package org.javahw.repository.impl;

import org.javahw.model.Role;
import org.javahw.repository.RoleRepository;
import org.javahw.util.HibernateUtil;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;

import java.util.Optional;

public class RoleRepositoryImpl implements RoleRepository {
   private final SessionFactory sessionFactory = HibernateUtil.getSessionFactory();

    @Override
    public Role create(Role role) {
//        try (Session session = sessionFactory.openSession()) {
//            session.save(role);
//        }
//        !!! Do not use save() when works with related tables. Otherwise - no data in related tables, without any notices about it!!!
        Transaction transaction = null;
        try (Session session = sessionFactory.openSession()) {
            transaction = session.getTransaction();
            transaction.begin();
            session.persist(role);
            transaction.commit();
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
        }
        return role;
    }

    @Override
    public Optional<Role> findById(Long id) {
        try (Session session = sessionFactory.openSession()) {
            return Optional.ofNullable(session.get(Role.class, id));
        }
    }
}
