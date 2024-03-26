package com.nevermore.demo.base

/**
 *
 * @author: xct
 * create on: 2022/8/26 11:14
 *
 */
sealed interface PageState<out T>

object Loading : PageState<Nothing>

data class Success<T>(val value: T) : PageState<T>

data class Failure(val errorCode: Int, val errorMessage: String?) : PageState<Nothing>







