package accounts.controller;

import accounts.model.Account;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class AccountController {

    @RequestMapping(value = "/account", method = RequestMethod.POST)
    public Account create(@RequestBody Account account) {

        System.out.println("################## controller #########################");
        System.out.println(account);
        System.out.println("################## controller end #########################");

        return account;
    }
}
