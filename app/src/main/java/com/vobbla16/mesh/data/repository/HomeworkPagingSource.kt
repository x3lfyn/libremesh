package com.vobbla16.mesh.data.repository

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.vobbla16.mesh.common.localDateTimeNow
import com.vobbla16.mesh.common.toStr
import com.vobbla16.mesh.data.remote.MeshApi
import com.vobbla16.mesh.data.remote.dto.homework.toDomain
import com.vobbla16.mesh.domain.model.homework.HomeworkItemsForDate
import kotlinx.datetime.DatePeriod
import kotlinx.datetime.LocalDate
import kotlinx.datetime.minus
import kotlinx.datetime.plus

class HomeworkPagingSource(
    private val meshApi: MeshApi,
    private val token: String,
    private val studentId: Int
) : PagingSource<LocalDate, HomeworkItemsForDate>() {
    override fun getRefreshKey(state: PagingState<LocalDate, HomeworkItemsForDate>): LocalDate? {
        return state.anchorPosition?.let { anchorPosition ->
            val anchorPage = state.closestPageToPosition(anchorPosition)
            anchorPage?.prevKey
                ?.minus(DatePeriod(days = 1))
                ?: anchorPage?.nextKey?.plus(DatePeriod(days = 1))
        }
    }

    override suspend fun load(params: LoadParams<LocalDate>): LoadResult<LocalDate, HomeworkItemsForDate> {
        return try {
            val dateToLoad = params.key ?: localDateTimeNow().date

            val prevKey = params.key?.plus(DatePeriod(days = params.loadSize))
            val nextKey = dateToLoad.minus(DatePeriod(days = params.loadSize))

            val data = meshApi.getHomework(
                token = token,
                studentId = studentId,
                beginDate = nextKey.plus(DatePeriod(days = 1)).toStr(),
                endDate = dateToLoad.toStr()
            )
                .map { it.toDomain() }
                .groupBy { it.datePreparedFor }
                .map { HomeworkItemsForDate(it.key, it.value) }
                .sortedBy { it.date }
                .reversed()

            LoadResult.Page(
                data = data,
                prevKey = prevKey,
                nextKey = nextKey
            )
        } catch (e: Exception) {
            LoadResult.Error(e)
        }
    }
}