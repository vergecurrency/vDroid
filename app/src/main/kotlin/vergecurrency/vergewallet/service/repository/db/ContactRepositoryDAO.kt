package vergecurrency.vergewallet.service.repository.db

import org.greenrobot.greendao.annotation.Entity
import org.greenrobot.greendao.annotation.Id
import org.greenrobot.greendao.annotation.Property

@Entity(nameInDb = "contact")
class ContactRepositoryDAO {

    @Id(autoincrement = true)
    private val id: Long = 0
    @Property(nameInDb = "name")
    private val name: String? = null
    @Property(nameInDb = "address")
    private val address: String? = null


}
