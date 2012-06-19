package com.minosiants.supperblog.common

import com.minosiants.supperblog.common.dao.MongoDataBaseComponent
import com.minosiants.supperblog.common.service.UserServiceComponent
import com.minosiants.supperblog.common.model.Implicits

trait Common extends UserServiceComponent with MongoDataBaseComponent with Implicits