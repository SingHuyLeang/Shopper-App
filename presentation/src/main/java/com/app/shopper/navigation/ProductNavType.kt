package com.app.shopper.navigation

import android.os.*
import androidx.annotation.RequiresApi
import androidx.navigation.NavType
import com.app.shopper.model.UIProductModel
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json
import java.net.*
import java.util.Base64

val productNavType = object : NavType<UIProductModel>(isNullableAllowed = false) {
	override fun get(bundle: Bundle, key: String): UIProductModel? {
		if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
			return bundle.getParcelable(key, UIProductModel::class.java)
		}
		return bundle.getParcelable(key) as? UIProductModel
	}

	@RequiresApi(Build.VERSION_CODES.O)
	override fun parseValue(value: String): UIProductModel {
		val item = Json.decodeFromString<UIProductModel>(value)
		return item.copy(
			image = URLDecoder.decode(item.image,"UTF-8"),
			description = String(Base64.getDecoder().decode(item.description.replace("_","/"))),
			title = String(Base64.getDecoder().decode(item.title.replace("_","/")))
		)
	}

	@RequiresApi(Build.VERSION_CODES.O)
	override fun serializeAsValue(value: UIProductModel): String {
		return Json.encodeToString(
			value.copy(
				image = URLEncoder.encode(value.image, "UTF-8"),
				description = String(Base64.getEncoder().encode(value.description.toByteArray())).replace("/","_"),
				title = String(Base64.getEncoder().encode(value.title.toByteArray())).replace("/","_")
			)
		)
	}

	override fun put(bundle: Bundle, key: String, value: UIProductModel) {
		bundle.putParcelable(key, value)
	}

}