package br.com.wswork.bestcommerceapi.repository;

import br.com.wswork.bestcommerceapi.model.User;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.security.core.userdetails.UserDetails;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@DataJpaTest
public class UserRepositoryTest {

    @Autowired
    private TestEntityManager entityManager;

    @Autowired
    private UserRepository userRepository;

    @Test
    public void testFindByLogin() {
        User user = new User();
        user.setLogin("testuser");
        entityManager.persist(user);

        UserDetails userDetails = userRepository.findByLogin("testuser");

        assertNotNull(userDetails);
        assertEquals(user.getLogin(), userDetails.getUsername());
    }

    @Test
    public void testFindByLogin_WithNonExistingLogin() {
        UserDetails userDetails = userRepository.findByLogin("nonexistentuser");

        assertEquals(null, userDetails);
    }
}
