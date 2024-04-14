package com.zinc.domain.usecases.other

import android.util.Log
import com.zinc.common.models.BucketStatus
import com.zinc.common.models.OtherProfileHomeData
import com.zinc.common.models.OtherProfileLoad
import com.zinc.common.models.ProfileResponse
import com.zinc.common.models.TopProfile
import com.zinc.domain.models.OtherBucketListResponse
import com.zinc.domain.models.OtherFollowDataResponse
import com.zinc.domain.repository.OtherRepository
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.joinAll
import kotlinx.coroutines.launch
import javax.inject.Inject

class LoadOtherInfo @Inject constructor(
    private val otherRepository: OtherRepository
) {
    suspend operator fun invoke(
        token: String,
        userId: String
    ): OtherProfileLoad? {

        var otherProfileLoad: OtherProfileLoad? = null
        coroutineScope {
            launch {

                var profileResponse: ProfileResponse? = null
                var bucketListResponse: OtherBucketListResponse? = null
                var followResponse: OtherFollowDataResponse? = null

                joinAll(launch {
                    profileResponse = otherRepository.loadOtherProfile(token, userId)
                }, launch {
                    bucketListResponse = otherRepository.loadOtherBucketList(token, userId)
                }, launch {
                    followResponse = otherRepository.loadOtherFollow(token, userId)
                })

                Log.e(
                    "ayhan",
                    "LoadOtherInfo : $profileResponse , $bucketListResponse, $followResponse"
                )
                otherProfileLoad =
                    if (profileResponse?.success == true && bucketListResponse?.success == true && followResponse?.success == true) {

                        val profile = profileResponse?.data!!
                        val bucketList = bucketListResponse?.data!!
                        val follow = followResponse?.data!!

                        OtherProfileLoad(
                            isSuccess = true,
                            data = OtherProfileHomeData(
                                profile = TopProfile(
                                    isFollowed = false,
                                    name = profile.name,
                                    imgUrl = profile.imgUrl,
                                    percent = bucketList.completedCount.toFloat() / bucketList.totalCount.toFloat(),
                                    badgeType = profile.badgeImgUrl,
                                    badgeTitle = profile.badgeTitle,
                                    bio = profile.bio,
                                    followerCount = follow.followerCount.toString(),
                                    followingCount = follow.followingCount.toString()
                                ), bucketList = bucketList.bucketlist.map {
                                    OtherProfileHomeData.OtherBucketInfo(
                                        title = it.title,
                                        bucketId = it.id,
                                        isProgress = it.status == BucketStatus.PROGRESS
                                    )
                                }
                            )
                        )
                    } else {
                        OtherProfileLoad(
                            isSuccess = false,
                            data = null
                        )
                    }
            }
        }

        return otherProfileLoad
    }
}