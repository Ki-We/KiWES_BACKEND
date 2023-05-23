package server.api.kiwes.base.repository;

import org.springframework.stereotype.Repository;
import server.api.kiwes.domain.Foo;

import java.util.Optional;

@Repository
public class FooRepository {

    public Optional<Foo> findFoo(String name, String title){
        Foo foo = new Foo(name, title);

        return Optional.of(foo);
    }

}
