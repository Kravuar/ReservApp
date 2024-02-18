package net.kravuar.business.persistence;

import org.junit.jupiter.api.TestInstance;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

@DataJpaTest
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class JPABusinessPersistenceAdapterTest {
//    @Autowired
//    private BusinessRepository repository;
//    private final BusinessMapper mapper = Mappers.getMapper(BusinessMapper.class);
//    private JPABusinessPersistenceAdapter persistenceAdapter;
//
//    @BeforeAll
//    void init() {
//        persistenceAdapter = new JPABusinessPersistenceAdapter(
//                mapper,
//                repository
//        );
//    }
//
//    @Test
//    void givenExistingId_whenFindById_thenReturnsBusiness() {
//        // given
//        long id = 1L;
//        BusinessModel model = new BusinessModel();
//        model.setId(id);
//        repository.save(model);
//
//        // when
//        Business result = assertDoesNotThrow(() -> persistenceAdapter.findById(id));
//
//        // then
//        assertEquals(id, result.getId());
//    }
//
//    @Test
//    void givenNonExistingId_whenFindById_thenThrowsBusinessNotFoundException() {
//        // when & then
//        assertThrows(BusinessNotFoundException.class, () -> persistenceAdapter.findById(1L));
//    }
//
//    @Test
//    void givenExistingEmail_whenFindByEmail_thenReturnsBusiness() {
//        // given
//        String mail = "test@be.be";
//        BusinessModel model = new BusinessModel();
//        model.setEmail(mail);
//        repository.save(model);
//
//        // when
//        Business result = assertDoesNotThrow(() -> persistenceAdapter.findByEmail(mail));
//
//        // then
//        assertEquals(mail, result.getEmail());
//    }
//
//    @Test
//    void givenNonExistingEmail_whenFindByEmail_thenThrowsBusinessNotFoundException() {
//        assertThrows(BusinessNotFoundException.class, () -> persistenceAdapter.findByEmail("nonexisting@example.com"));
//    }
//
//    @Test
//    void givenExistingEmail_whenExistsByEmail_thenReturnsTrue() {
//        // given
//        String mail = "test@example.com";
//        BusinessModel model = new BusinessModel();
//        model.setEmail(mail);
//        repository.save(model);
//
//        // when
//        boolean exists = persistenceAdapter.existsByEmail(mail);
//
//        // then
//        assertTrue(exists);
//    }
//
//    @Test
//    void givenNonExistingEmail_whenExistsByEmail_thenReturnsFalse() {
//        // when
//        boolean exists = persistenceAdapter.existsByEmail("nonexisting@be.be");
//
//        // then
//        assertFalse(exists);
//    }
//
//    @Test
//    void givenBusiness_whenSave_thenSavesBusiness() {
//        // given
//        String mail = "test@example.com";
//        Business business = Business.builder()
//                .email(mail)
//                .build();
//
//        // when
//        Business result = persistenceAdapter.save(business);
//
//        // then
//        assertNotNull(result.getId());
//        assertEquals(mail, result.getEmail());
//    }
}