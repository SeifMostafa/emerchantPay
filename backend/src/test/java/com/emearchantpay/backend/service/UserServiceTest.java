package com.emearchantpay.backend.service;

import com.emearchantpay.backend.model.Role;
import com.emearchantpay.backend.model.User;
import com.emearchantpay.backend.repository.RoleRepository;
import com.emearchantpay.backend.repository.UserRepository;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.jupiter.api.DisplayName;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;

import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@SpringBootTest
@RunWith(MockitoJUnitRunner.class)
public class UserServiceTest {
    @Mock
    UserDetailsServiceImpl userDetailsService;
    @Mock
    AuthenticationManager authenticationManager;
    @Mock
    UserRepository userRepository;
    @Mock
    RoleRepository roleRepository;
    @Mock
    SecurityContextHolder securityContextHolder;
    @InjectMocks
    UserServiceImpl userService;


    User user;
    @Before
    public void before(){
        user = User.builder()
                .name("Seif")
                .email("csseifms@gmail.com")
                .phone("11111111")
                .password("OkokOK")
                .balance(100)
                .build();
    }
    @DisplayName("check create admin")
    @Test
    public void testCreateAdminUser(){
        Role roleAdmin = Role.builder().name("ROLE_ADMIN").build();
        when(roleRepository.findByName("ROLE_ADMIN")).thenReturn(roleAdmin);
        userService.create(user,true);

        Assert.assertEquals(1,user.getRoles().size());
        Assert.assertTrue(user.getRoles().contains(roleAdmin));

        verify(userRepository,times(1)).save(any());
    }
    @DisplayName("check create user")
    @Test
    public void testCreateNormalUser(){
        Role roleUser = Role.builder().name("ROLE_USER").build();
        when(roleRepository.findByName("ROLE_USER")).thenReturn(roleUser);
        userService.create(user,false);

        Assert.assertEquals(1,user.getRoles().size());
        Assert.assertTrue(user.getRoles().contains(roleUser));

        verify(userRepository,times(1)).save(any());
    }
    @DisplayName("test holding amount for enough balance")
    @Test
    public void testHoldingAmountSuccess(){
        Authentication authentication = Mockito.mock(Authentication.class);
        SecurityContext securityContext = Mockito.mock(SecurityContext.class);
        Mockito.when(securityContext.getAuthentication()).thenReturn(authentication);
        SecurityContextHolder.setContext(securityContext);
        when(userRepository.findByEmail(any())).thenReturn(Optional.ofNullable(user));
        int onholdBalanceBeforeCalling = user.getOnholdBalance();
        int balanceBeforeCalling = user.getBalance();

        userService.holdAmount(10);

        Assert.assertEquals(10+onholdBalanceBeforeCalling,user.getOnholdBalance());
        Assert.assertEquals(balanceBeforeCalling-10,user.getBalance());
        verify(userRepository,times(1)).save(any());

        user.setOnholdBalance(0);
        user.setBalance(100);
    }
    @DisplayName("check holding amount for not enough balance")
    @Test
    public void testHoldingAmountFail(){
        Authentication authentication = Mockito.mock(Authentication.class);
        SecurityContext securityContext = Mockito.mock(SecurityContext.class);
        Mockito.when(securityContext.getAuthentication()).thenReturn(authentication);
        SecurityContextHolder.setContext(securityContext);
        when(userRepository.findByEmail(any())).thenReturn(Optional.ofNullable(user));
        user.setBalance(10);
        int onholdBalanceBeforeCalling = user.getOnholdBalance();
        int balanceBeforeCalling = user.getBalance();

        boolean held = userService.holdAmount(100);

        Assert.assertFalse(held);
        Assert.assertEquals(onholdBalanceBeforeCalling,user.getOnholdBalance());
        Assert.assertEquals(balanceBeforeCalling,user.getBalance());
        verify(userRepository,times(0)).save(any());
    }
    @DisplayName("test un-holding amount")
    @Test
    public void testUn_holdAmountSuccess(){
        Authentication authentication = Mockito.mock(Authentication.class);
        SecurityContext securityContext = Mockito.mock(SecurityContext.class);
        Mockito.when(securityContext.getAuthentication()).thenReturn(authentication);
        SecurityContextHolder.setContext(securityContext);
        when(userRepository.findByEmail(any())).thenReturn(Optional.ofNullable(user));
        int onholdBalanceBeforeCalling = user.getOnholdBalance();
        int balanceBeforeCalling = user.getBalance();

        userService.unholdAmount(10);

        Assert.assertEquals(onholdBalanceBeforeCalling-10,user.getOnholdBalance());
        Assert.assertEquals(balanceBeforeCalling+10,user.getBalance());
        verify(userRepository,times(1)).save(any());

        user.setOnholdBalance(0);
        user.setBalance(100);
    }

    @DisplayName("check charge amount")
    @Test
    public void testChargeAmount(){
        Authentication authentication = Mockito.mock(Authentication.class);
        SecurityContext securityContext = Mockito.mock(SecurityContext.class);
        Mockito.when(securityContext.getAuthentication()).thenReturn(authentication);
        SecurityContextHolder.setContext(securityContext);
        when(userRepository.findByEmail(any())).thenReturn(Optional.ofNullable(user));
        int balanceBeforeCalling = user.getBalance();

        userService.charge(10);

        Assert.assertEquals(balanceBeforeCalling-10,user.getBalance());
        verify(userRepository,times(1)).save(any());

        user.setBalance(100);
    }
    @DisplayName("check refund amount")
    @Test
    public void testRefundAmount(){
        Authentication authentication = Mockito.mock(Authentication.class);
        SecurityContext securityContext = Mockito.mock(SecurityContext.class);
        Mockito.when(securityContext.getAuthentication()).thenReturn(authentication);
        SecurityContextHolder.setContext(securityContext);
        when(userRepository.findByEmail(any())).thenReturn(Optional.ofNullable(user));
        int balanceBeforeCalling = user.getBalance();

        userService.refund(10);

        Assert.assertEquals(balanceBeforeCalling+10,user.getBalance());
        verify(userRepository,times(1)).save(any());

        user.setBalance(100);
    }
}
