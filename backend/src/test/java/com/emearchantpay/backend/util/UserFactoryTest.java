package com.emearchantpay.backend.util;

import com.emearchantpay.backend.model.User;
import org.junit.Assert;
import org.junit.Test;
import org.junit.jupiter.api.DisplayName;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.io.File;
import java.io.FileInputStream;
import java.util.List;

import static org.assertj.core.api.AssertionsForClassTypes.assertThatThrownBy;
@SpringBootTest
@RunWith(MockitoJUnitRunner.class)
public class UserFactoryTest {
    @Mock
    PasswordEncoder passwordEncoder;
    @InjectMocks
    UserFactory userFactory;

    @DisplayName("check if passed wrong formatted file to UserFactory")
    @Test
    public void testWrongFormatFileFail() throws Exception {
        File file = new File("src/test/resources/adminsWithWrongFormat.csv");
        FileInputStream fileInputStream = new FileInputStream(file);

        assertThatThrownBy(() -> userFactory.getUsers(fileInputStream))
                .isInstanceOf(Exception.class);
    }
    @DisplayName("check if passed correct formatted file to MerchantFactory")
    @Test
    public void testCorrectFileSuccess() throws Exception {
        File file = new File("src/test/resources/admins.csv");
        FileInputStream fileInputStream = new FileInputStream(file);

        List<User> users = userFactory.getUsers(fileInputStream);

        Assert.assertTrue(!users.isEmpty());
        Assert.assertNotNull(users.get(0).getName());
    }
}
