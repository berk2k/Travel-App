package com.berkpolat.termproject.interfaces


import androidx.lifecycle.LiveData
import androidx.room.*
import com.berkpolat.termproject.bookDetails.Book
import com.berkpolat.termproject.cards.CreditCard
import com.berkpolat.termproject.User
import com.berkpolat.termproject.tour.Tour

@Dao
interface AppDao {
    @Insert
    suspend fun insertUser(user: User)

    @Update
    suspend fun updateUser(user: User)

    @Query("SELECT * FROM users WHERE email = :email")
    fun getUserByEmail(email: String): User?

    @Query("SELECT account_money FROM users WHERE user_name = :userName")
    fun getAccMoneyByUserName(userName: String): Float?

    @Query("SELECT * FROM users WHERE user_name = :userName")
    fun getUserByUserName(userName: String): User?

    @Query("SELECT user_name FROM users")
    fun getAllUserNames(): List<String>

    @Query("SELECT email FROM users")
    fun getAllEmails(): List<String>

    @Query("SELECT * FROM users")
    fun getAllUsers(): List<User>

    @Insert
    suspend fun insertCard(creditCard: CreditCard)

    @Update
    suspend fun updateCard(creditCard: CreditCard)

    @Delete
    suspend fun deleteCard(creditCard: CreditCard)


    @Query("SELECT * FROM credit_cards WHERE card_no = :card_no")
    fun getCardByCardNo(card_no: String): CreditCard?

    @Query("SELECT money FROM credit_cards WHERE card_no = :card_no")
    fun getMoneyByCardNo(card_no: String): Float?

    @Query("SELECT expiry_date FROM credit_cards WHERE card_no = :card_no")
    fun getDateByCardNo(card_no: String): Int?

    @Query("SELECT cvv FROM credit_cards WHERE card_no = :card_no")
    fun getCvvByCardNo(card_no: String): Int?

    @Query("SELECT card_no FROM credit_cards")
    fun getAllCardNumbers(): List<String>

    @Insert
    suspend fun insertTour(tour: Tour)

    @Update
    suspend fun updateTour(tour: Tour)

    @Delete
    suspend fun deleteTour(tour: Tour)

    @Query("SELECT * FROM tours WHERE tourId = :tourId")
    fun getTourByTourId(tourId: Int): Tour?

    @Query("SELECT * FROM tours WHERE destination = :destination")
    fun getTourByDestination(destination: String): Tour?

    @Query("SELECT destination FROM tours")
    fun getAllDestinations(): List<String>

    @Query("SELECT cost FROM tours WHERE tourId = :tourId")
    fun getCostByTourId(tourId: Int): Float?

    @Query("SELECT * FROM tours")
    fun getAllTours(): LiveData<List<Tour>>

    @Query("SELECT COUNT(*) FROM tours")
    fun getTourSize(): Int?

    @Insert
    suspend fun insertBook(book: Book)

    @Update
    suspend fun updateBook(book: Book)

    @Delete
    suspend fun deleteBook(book: Book)

    @Query("SELECT * FROM booked")
    fun getAllBookInf(): LiveData<List<Book>>

    @Query("SELECT * FROM booked WHERE userName = :userName")
    fun getBookedToursByUser(userName: String): LiveData<List<Book>>

    @Query("SELECT * FROM booked WHERE bookId = :bookId")
    fun getBookedTourById(bookId: Int): Book?

    @Query("SELECT cost FROM booked WHERE bookId = :bookId")
    fun getCostById(bookId: Int): Float?


}