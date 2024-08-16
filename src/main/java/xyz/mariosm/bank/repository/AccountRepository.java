package xyz.mariosm.bank.repository;

import org.bson.types.ObjectId;
import org.springframework.data.repository.CrudRepository;
import xyz.mariosm.bank.data.Account;
import xyz.mariosm.bank.data.AccountTypes;

import java.util.Optional;

public interface AccountRepository extends CrudRepository<Account, ObjectId> {
    Optional<Account> findByUsernameAndType(String username, AccountTypes type);
}
