package com.core_will_soft;

import org.junit.jupiter.api.Test;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.jdbc.SqlConfig;

import static com.core_will_soft.controller.UserController.BASE_URL;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

public class GetUsersIT extends BaseIT {

    @Test
    @WithMockUser(username = "admin")
    @Sql("classpath:sql/insertMultipleUsers.sql")
    @Sql(scripts = "classpath:sql/clearTables.sql",
            executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD,
            config = @SqlConfig(transactionMode = SqlConfig.TransactionMode.ISOLATED))
    void testGetAll() throws Exception {
        mockMvc
                .perform(get(BASE_URL)
                        .param("page", "0")
                        .param("size", "10"))
                .andExpect(status().isOk())
                .andExpect(content().json(readExpectedResponse("get-all-response")));
    }

    @Test
    @WithMockUser(username = "admin")
    @Sql("classpath:sql/insertMultipleUsers.sql")
    @Sql(scripts = "classpath:sql/clearTables.sql",
            executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD,
            config = @SqlConfig(transactionMode = SqlConfig.TransactionMode.ISOLATED))
    void testPagination() throws Exception {
        mockMvc.perform(get(BASE_URL)
                        .param("page", "2")
                        .param("size", "1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.data.length()").value(1))
                .andExpect(jsonPath("$.totalCount").value(10));
    }
}

