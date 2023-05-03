package com.example.jobposter.models

data class JobModel(
    var jobId: String? = null,
    var jobTitle: String? = null,
    var jobType: String? = null,
    var payment: String? = null,
    var description: String? = null,
    var location: String? = null,
    var contactNo: String? = null

)
