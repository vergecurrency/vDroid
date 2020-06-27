package vergecurrency.vergewallet.service.model

import io.realm.RealmObject
import io.realm.annotations.PrimaryKey
import io.realm.annotations.RealmField


open class VDroidContact(@PrimaryKey() var id: Int = 0,
                         @RealmField(name = "address") var address: String? = null,
                         @RealmField(name = "name") var name: String? = null) :
        RealmObject() {

}