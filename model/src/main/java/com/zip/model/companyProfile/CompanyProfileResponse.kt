package com.zip.model.companyProfile

data class CompanyProfileResponse(
    val companyProfiles: List<CompanyProfileMain>?
)

data class CompanyProfileMain(
    val symbol: String?,
    val profile: Profile?
)

data class Profile(
    val price: Double?,
    val mktCap: String?,
    val lastDiv: String?,
    val changes: Double?,
    val changesPercentage: String?,
    val companyName: String?,
    val industry: String?,
    val sector: String?,
    val image: String?
)