package accounts.controller;

import accounts.model.vo.AccountVO;
import accounts.service.AccountService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import static org.junit.Assert.assertEquals;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
public class AccountControllerTest {

    @Mock
    private AccountService accountService;

    @InjectMocks
    private AccountController accountController;

    @Test
    public void shouldCreateAccountByCallingTheService() {

        final AccountVO request = new AccountVO();
        final AccountVO expectedResponse = new AccountVO();

        when(accountService.create(any(AccountVO.class))).thenReturn(expectedResponse);
        final AccountVO actualResponse = accountController.create(request);

        assertEquals("The Controller should return directly the Account object received from the Service", expectedResponse, actualResponse);
        verify(accountService, times(1)).create(request);
    }
}
