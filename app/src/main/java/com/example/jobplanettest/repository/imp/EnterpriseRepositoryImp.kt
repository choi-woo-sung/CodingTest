package com.example.jobplanettest.repository.imp

import javax.inject.Inject

class EnterpriseRepositoryImp @Inject constructor(
    private val db: FirebaseFirestore,
    private val storage: FirebaseStorage
){

}
