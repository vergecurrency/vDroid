package vergecurrency.vergewallet.service.model

import androidx.appcompat.widget.SwitchCompat

data class PermissionConstruct(val permission : String,val  permissionCode : Int,val explanationMessage : String, val component : SwitchCompat)