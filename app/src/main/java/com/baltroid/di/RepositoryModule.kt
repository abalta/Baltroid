package com.baltroid.di

import com.baltroid.core.data.repository.AuthRepositoryImpl
import com.baltroid.core.data.repository.AuthorRepositoryImpl
import com.baltroid.core.data.repository.CommentRepositoryImpl
import com.baltroid.core.data.repository.FavoriteRepositoryImpl
import com.baltroid.core.data.repository.OriginalRepositoryImpl
import com.baltroid.core.data.repository.WelcomeRepositoryImpl
import com.hitreads.core.domain.repository.AuthRepository
import com.hitreads.core.domain.repository.AuthorRepository
import com.hitreads.core.domain.repository.CommentRepository
import com.hitreads.core.domain.repository.FavoriteRepository
import com.hitreads.core.domain.repository.OriginalRepository
import com.hitreads.core.domain.repository.WelcomeRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent
import dagger.hilt.android.scopes.ViewModelScoped

@Module
@InstallIn(ViewModelComponent::class)
interface RepositoryModule {
    @Binds
    @ViewModelScoped
    fun bindOriginalRepository(originalRepositoryImpl: OriginalRepositoryImpl): OriginalRepository

    @Binds
    @ViewModelScoped
    fun bindAuthRepository(authRepositoryImpl: AuthRepositoryImpl): AuthRepository

    @Binds
    @ViewModelScoped
    fun bindCommentRepository(commentRepositoryImpl: CommentRepositoryImpl): CommentRepository

    @Binds
    @ViewModelScoped
    fun bindWelcomeRepository(welcomeRepositoryImpl: WelcomeRepositoryImpl): WelcomeRepository

    @Binds
    @ViewModelScoped
    fun bindFavoriteRepository(favoriteRepository: FavoriteRepositoryImpl): FavoriteRepository

    @Binds
    @ViewModelScoped
    fun bindAuthorRepository(authorRepository: AuthorRepositoryImpl): AuthorRepository

    /*
    @Binds
    @ViewModelScoped
    fun bindTagRepository(tagRepositoryImpl: TagRepositoryImpl): TagRepository

    @Binds
    @ViewModelScoped
    fun bindBookmarkRepository(bookmarkRepository: BookmarkRepositoryImpl): BookmarkRepository
     */

}