package com.cryptopia.android.di

import javax.inject.Scope


/**
 * Created by robertzzy on 08/12/17.
 */

@Scope
@Retention(AnnotationRetention.RUNTIME)
@Target(AnnotationTarget.TYPE, AnnotationTarget.FUNCTION)
annotation class FragmentScoped