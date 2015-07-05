package accounts.controller;

import accounts.model.vo.AccountVO;
import accounts.service.AccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class AccountController {

    @Autowired
    private AccountService accountService;

    @RequestMapping(value = "/account", method = RequestMethod.POST)
    public AccountVO create(@RequestBody AccountVO accountVO) {

        return accountService.create(accountVO);
    }
}
