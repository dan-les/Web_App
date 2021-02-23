package github.rootuss.lang;

import github.rootuss.HibernateUtil;

import java.util.List;
import java.util.Optional;

public class LangRepository {


    public List<Lang> findAll() {
        var session = HibernateUtil.getSessionFactory().openSession();
        var transaction = session.beginTransaction();

        //zwrócenie wsystkich języków (można użyć nazwy klasy Lang a nie tabeli languages)
        //języku są typu lang, dlatego Lang.class
        //zwracamy jako lista
        var result = session.createQuery("from Lang", Lang.class).list();

        transaction.commit();
        session.close();

        return result;

    }

    public Optional<Lang> findById(Integer id) {
        var session = HibernateUtil.getSessionFactory().openSession();
        var transaction = session.beginTransaction();

        var result = session.get(Lang.class, id);

        transaction.commit();
        session.close();

        return Optional.ofNullable(result);
    }

}
