package org.example;


import org.hibernate.query.Query;

public class Main {
    public static void main(String[] args) {

        try (var session = HibernateConfig.getSessionFactory().openSession()) {
            final var transaction = session.beginTransaction();

            var weightDivision = DepartmentEntity
                    .builder()
                    .name("Lightweight")
                    .build();

            session.save(weightDivision);

            var fighter = new EmployeeEntity();
            fighter.setName("Conor McGregor");
            fighter.setEmail("conor@ufc.com");
            fighter.setDepartment(weightDivision);

            session.save(fighter);

            fighter = new EmployeeEntity();
            fighter.setName("Khabib Nurmagomedov");
            fighter.setEmail("khabib@ufc.com");
            fighter.setDepartment(weightDivision);

            session.save(fighter);

            transaction.commit();

            Query<EmployeeEntity> query = session.createQuery("FROM EmployeeEntity e JOIN FETCH e.department", EmployeeEntity.class);

            query.list().forEach(System.out::println);
        }
    }
}