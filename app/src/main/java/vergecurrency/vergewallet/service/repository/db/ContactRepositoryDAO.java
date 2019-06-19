package vergecurrency.vergewallet.service.repository.db;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Id;
import org.greenrobot.greendao.annotation.Property;

@Entity(nameInDb = "contact")
public class ContactRepositoryDAO {

    @Id(autoincrement = true)
    private long id;
    @Property(nameInDb = "name")
    private String name;
    @Property(nameInDb = "address")
    private String address;



}
