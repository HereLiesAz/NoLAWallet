package com.hereliesaz.nolawallet.data

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

/**
 * The extension property to create the DataStore.
 */
val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = "user_identity")

/**
 * A repository for managing license data.
 *
 * @param context The application context.
 */
class LicenseRepository(private val context: Context) {

    /**
     * The keys for the license data.
     */
    private object Keys {
        val LICENSE_NO = stringPreferencesKey("license_no")
        val AUDIT_NO = stringPreferencesKey("audit_no")
        val FIRST_NAME = stringPreferencesKey("first_name")
        val LAST_NAME = stringPreferencesKey("last_name")
        val DOB = stringPreferencesKey("dob")
        val ISSUE_DATE = stringPreferencesKey("issue_date")
        val EXPIRY_DATE = stringPreferencesKey("expiry_date")
        val HEIGHT = stringPreferencesKey("height")
        val WEIGHT = stringPreferencesKey("weight")
        val EYES = stringPreferencesKey("eyes")
        val SEX = stringPreferencesKey("sex")
        val ADDRESS_STREET = stringPreferencesKey("address_street")
        val ADDRESS_CSZ = stringPreferencesKey("address_csz")
        val PHOTO_PATH = stringPreferencesKey("photo_path")
    }

    val licenseFlow: Flow<IdentityLicense> = context.dataStore.data.map { prefs ->
        IdentityLicense(
            licenseNumber = prefs[Keys.LICENSE_NO] ?: "",
            auditNumber = prefs[Keys.AUDIT_NO] ?: "",
            firstName = prefs[Keys.FIRST_NAME] ?: "",
            lastName = prefs[Keys.LAST_NAME] ?: "",
            dob = prefs[Keys.DOB] ?: "",
            issueDate = prefs[Keys.ISSUE_DATE] ?: "",
            expiryDate = prefs[Keys.EXPIRY_DATE] ?: "",
            height = prefs[Keys.HEIGHT] ?: "",
            weight = prefs[Keys.WEIGHT] ?: "",
            eyes = prefs[Keys.EYES] ?: "",
            sex = prefs[Keys.SEX] ?: "",
            addressStreet = prefs[Keys.ADDRESS_STREET] ?: "",
            addressCityStateZip = prefs[Keys.ADDRESS_CSZ] ?: "",
            photoPath = prefs[Keys.PHOTO_PATH] ?: ""
        )
    }

    suspend fun saveLicense(data: IdentityLicense) {
        context.dataStore.edit { prefs ->
            prefs[Keys.LICENSE_NO] = data.licenseNumber
            prefs[Keys.AUDIT_NO] = data.auditNumber
            prefs[Keys.FIRST_NAME] = data.firstName
            prefs[Keys.LAST_NAME] = data.lastName
            prefs[Keys.DOB] = data.dob
            prefs[Keys.ISSUE_DATE] = data.issueDate
            prefs[Keys.EXPIRY_DATE] = data.expiryDate
            prefs[Keys.HEIGHT] = data.height
            prefs[Keys.WEIGHT] = data.weight
            prefs[Keys.EYES] = data.eyes
            prefs[Keys.SEX] = data.sex
            prefs[Keys.ADDRESS_STREET] = data.addressStreet
            prefs[Keys.ADDRESS_CSZ] = data.addressCityStateZip
            prefs[Keys.PHOTO_PATH] = data.photoPath
        }
    }
}
