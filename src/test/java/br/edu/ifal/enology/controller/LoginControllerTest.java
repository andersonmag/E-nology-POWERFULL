package br.edu.ifal.enology.controller;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import br.edu.ifal.enology.repository.UserRepository;
import br.edu.ifal.enology.security.SecurityWeb;
import br.edu.ifal.enology.service.LoginDetailsService;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;

@RunWith(SpringRunner.class)
@WebMvcTest(LoginController.class)
@ContextConfiguration(classes = {LoginDetailsService.class, SecurityWeb.class})
public class LoginControllerTest {

    @MockBean
	private UserRepository userRepository;

	@Autowired
    private MockMvc mockMvc;

    @Test
    public void deveRetornarOkAoRequisitarPaginaIndex() throws Exception {

        this.mockMvc.perform(get("/").accept(MediaType.TEXT_HTML))
                    .andDo(print())
                    .andExpect(status().isOk());
    
    }
    
}