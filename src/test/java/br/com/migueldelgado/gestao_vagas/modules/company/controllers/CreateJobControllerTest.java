package br.com.migueldelgado.gestao_vagas.modules.company.controllers;

import br.com.migueldelgado.gestao_vagas.modules.company.dto.CreateJobDTO;
import br.com.migueldelgado.gestao_vagas.modules.company.entities.CompanyEntity;
import br.com.migueldelgado.gestao_vagas.modules.company.repositories.CompanyRepository;
import br.com.migueldelgado.gestao_vagas.utils.TestUtils;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

@ExtendWith(MockitoExtension.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@ActiveProfiles("test")
public class CreateJobControllerTest {

    private MockMvc mvc;

    @Autowired
    private CompanyRepository companyRepository;

    @Autowired
    private WebApplicationContext context;

    @BeforeEach
    public void setup() {
        mvc = MockMvcBuilders.webAppContextSetup(context)
                .apply(SecurityMockMvcConfigurers.springSecurity())
                .build();
    }

    @Test
    public void should_be_able_to_create_a_new_job() throws Exception {

        var company = CompanyEntity.builder().description("COMPANY_DESCRIPTION").email("email@company.com")
                .password("1234567890").name("COMPANY_NAME").website("www.company.com")
                .username("COMPANY_USERNAME").build();

        companyRepository.saveAndFlush(company);

        var createdJobDTO = CreateJobDTO.builder()
                .benefits("BENEFITS_TEST")
                .description("DESCRIPTION_TEST")
                .level("LEVEL_TEST")
                .build();

        var result = mvc.perform(MockMvcRequestBuilders.post("/company/job/")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(TestUtils.objectToJson(createdJobDTO))
                        .header("Authorization", TestUtils.generateToken(company.getId(),
                                "JAVAGAS_@123#"))
                )
                .andExpect(MockMvcResultMatchers.status().isOk());

        System.out.println(result);
    }
}
