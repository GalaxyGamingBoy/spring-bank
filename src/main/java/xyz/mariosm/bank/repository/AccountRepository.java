package xyz.mariosm.bank.repository;

import org.bson.types.ObjectId;
import org.springframework.data.repository.CrudRepository;
import xyz.mariosm.bank.data.Account;

public interface AccountRepository extends CrudRepository<Account, ObjectId> {
    Account findByUsername(String username);
}
