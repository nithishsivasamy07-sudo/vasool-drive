package com.yourcompany.loanledger.data.dao;

import android.database.Cursor;
import android.os.CancellationSignal;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.room.CoroutinesRoom;
import androidx.room.EntityDeletionOrUpdateAdapter;
import androidx.room.EntityInsertionAdapter;
import androidx.room.RoomDatabase;
import androidx.room.RoomSQLiteQuery;
import androidx.room.util.CursorUtil;
import androidx.room.util.DBUtil;
import androidx.sqlite.db.SupportSQLiteStatement;
import com.yourcompany.loanledger.data.entity.Loan;
import java.lang.Class;
import java.lang.Exception;
import java.lang.Long;
import java.lang.Object;
import java.lang.Override;
import java.lang.String;
import java.lang.SuppressWarnings;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.Callable;
import javax.annotation.processing.Generated;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlinx.coroutines.flow.Flow;

@Generated("androidx.room.RoomProcessor")
@SuppressWarnings({"unchecked", "deprecation"})
public final class LoanDao_Impl implements LoanDao {
  private final RoomDatabase __db;

  private final EntityInsertionAdapter<Loan> __insertionAdapterOfLoan;

  private final EntityDeletionOrUpdateAdapter<Loan> __updateAdapterOfLoan;

  public LoanDao_Impl(@NonNull final RoomDatabase __db) {
    this.__db = __db;
    this.__insertionAdapterOfLoan = new EntityInsertionAdapter<Loan>(__db) {
      @Override
      @NonNull
      protected String createQuery() {
        return "INSERT OR ABORT INTO `loans` (`id`,`customerId`,`principal`,`totalPayable`,`installmentCount`,`installmentAmount`,`startDate`,`isClosed`) VALUES (nullif(?, 0),?,?,?,?,?,?,?)";
      }

      @Override
      protected void bind(@NonNull final SupportSQLiteStatement statement,
          @NonNull final Loan entity) {
        statement.bindLong(1, entity.getId());
        statement.bindLong(2, entity.getCustomerId());
        statement.bindDouble(3, entity.getPrincipal());
        statement.bindDouble(4, entity.getTotalPayable());
        statement.bindLong(5, entity.getInstallmentCount());
        statement.bindDouble(6, entity.getInstallmentAmount());
        statement.bindLong(7, entity.getStartDate());
        final int _tmp = entity.isClosed() ? 1 : 0;
        statement.bindLong(8, _tmp);
      }
    };
    this.__updateAdapterOfLoan = new EntityDeletionOrUpdateAdapter<Loan>(__db) {
      @Override
      @NonNull
      protected String createQuery() {
        return "UPDATE OR ABORT `loans` SET `id` = ?,`customerId` = ?,`principal` = ?,`totalPayable` = ?,`installmentCount` = ?,`installmentAmount` = ?,`startDate` = ?,`isClosed` = ? WHERE `id` = ?";
      }

      @Override
      protected void bind(@NonNull final SupportSQLiteStatement statement,
          @NonNull final Loan entity) {
        statement.bindLong(1, entity.getId());
        statement.bindLong(2, entity.getCustomerId());
        statement.bindDouble(3, entity.getPrincipal());
        statement.bindDouble(4, entity.getTotalPayable());
        statement.bindLong(5, entity.getInstallmentCount());
        statement.bindDouble(6, entity.getInstallmentAmount());
        statement.bindLong(7, entity.getStartDate());
        final int _tmp = entity.isClosed() ? 1 : 0;
        statement.bindLong(8, _tmp);
        statement.bindLong(9, entity.getId());
      }
    };
  }

  @Override
  public Object insert(final Loan loan, final Continuation<? super Long> $completion) {
    return CoroutinesRoom.execute(__db, true, new Callable<Long>() {
      @Override
      @NonNull
      public Long call() throws Exception {
        __db.beginTransaction();
        try {
          final Long _result = __insertionAdapterOfLoan.insertAndReturnId(loan);
          __db.setTransactionSuccessful();
          return _result;
        } finally {
          __db.endTransaction();
        }
      }
    }, $completion);
  }

  @Override
  public Object update(final Loan loan, final Continuation<? super Unit> $completion) {
    return CoroutinesRoom.execute(__db, true, new Callable<Unit>() {
      @Override
      @NonNull
      public Unit call() throws Exception {
        __db.beginTransaction();
        try {
          __updateAdapterOfLoan.handle(loan);
          __db.setTransactionSuccessful();
          return Unit.INSTANCE;
        } finally {
          __db.endTransaction();
        }
      }
    }, $completion);
  }

  @Override
  public Object getActiveLoanForCustomer(final long customerId,
      final Continuation<? super Loan> $completion) {
    final String _sql = "SELECT * FROM loans WHERE customerId = ? AND isClosed = 0 LIMIT 1";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 1);
    int _argIndex = 1;
    _statement.bindLong(_argIndex, customerId);
    final CancellationSignal _cancellationSignal = DBUtil.createCancellationSignal();
    return CoroutinesRoom.execute(__db, false, _cancellationSignal, new Callable<Loan>() {
      @Override
      @Nullable
      public Loan call() throws Exception {
        final Cursor _cursor = DBUtil.query(__db, _statement, false, null);
        try {
          final int _cursorIndexOfId = CursorUtil.getColumnIndexOrThrow(_cursor, "id");
          final int _cursorIndexOfCustomerId = CursorUtil.getColumnIndexOrThrow(_cursor, "customerId");
          final int _cursorIndexOfPrincipal = CursorUtil.getColumnIndexOrThrow(_cursor, "principal");
          final int _cursorIndexOfTotalPayable = CursorUtil.getColumnIndexOrThrow(_cursor, "totalPayable");
          final int _cursorIndexOfInstallmentCount = CursorUtil.getColumnIndexOrThrow(_cursor, "installmentCount");
          final int _cursorIndexOfInstallmentAmount = CursorUtil.getColumnIndexOrThrow(_cursor, "installmentAmount");
          final int _cursorIndexOfStartDate = CursorUtil.getColumnIndexOrThrow(_cursor, "startDate");
          final int _cursorIndexOfIsClosed = CursorUtil.getColumnIndexOrThrow(_cursor, "isClosed");
          final Loan _result;
          if (_cursor.moveToFirst()) {
            final long _tmpId;
            _tmpId = _cursor.getLong(_cursorIndexOfId);
            final long _tmpCustomerId;
            _tmpCustomerId = _cursor.getLong(_cursorIndexOfCustomerId);
            final double _tmpPrincipal;
            _tmpPrincipal = _cursor.getDouble(_cursorIndexOfPrincipal);
            final double _tmpTotalPayable;
            _tmpTotalPayable = _cursor.getDouble(_cursorIndexOfTotalPayable);
            final int _tmpInstallmentCount;
            _tmpInstallmentCount = _cursor.getInt(_cursorIndexOfInstallmentCount);
            final double _tmpInstallmentAmount;
            _tmpInstallmentAmount = _cursor.getDouble(_cursorIndexOfInstallmentAmount);
            final long _tmpStartDate;
            _tmpStartDate = _cursor.getLong(_cursorIndexOfStartDate);
            final boolean _tmpIsClosed;
            final int _tmp;
            _tmp = _cursor.getInt(_cursorIndexOfIsClosed);
            _tmpIsClosed = _tmp != 0;
            _result = new Loan(_tmpId,_tmpCustomerId,_tmpPrincipal,_tmpTotalPayable,_tmpInstallmentCount,_tmpInstallmentAmount,_tmpStartDate,_tmpIsClosed);
          } else {
            _result = null;
          }
          return _result;
        } finally {
          _cursor.close();
          _statement.release();
        }
      }
    }, $completion);
  }

  @Override
  public Object getLoanById(final long id, final Continuation<? super Loan> $completion) {
    final String _sql = "SELECT * FROM loans WHERE id = ?";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 1);
    int _argIndex = 1;
    _statement.bindLong(_argIndex, id);
    final CancellationSignal _cancellationSignal = DBUtil.createCancellationSignal();
    return CoroutinesRoom.execute(__db, false, _cancellationSignal, new Callable<Loan>() {
      @Override
      @Nullable
      public Loan call() throws Exception {
        final Cursor _cursor = DBUtil.query(__db, _statement, false, null);
        try {
          final int _cursorIndexOfId = CursorUtil.getColumnIndexOrThrow(_cursor, "id");
          final int _cursorIndexOfCustomerId = CursorUtil.getColumnIndexOrThrow(_cursor, "customerId");
          final int _cursorIndexOfPrincipal = CursorUtil.getColumnIndexOrThrow(_cursor, "principal");
          final int _cursorIndexOfTotalPayable = CursorUtil.getColumnIndexOrThrow(_cursor, "totalPayable");
          final int _cursorIndexOfInstallmentCount = CursorUtil.getColumnIndexOrThrow(_cursor, "installmentCount");
          final int _cursorIndexOfInstallmentAmount = CursorUtil.getColumnIndexOrThrow(_cursor, "installmentAmount");
          final int _cursorIndexOfStartDate = CursorUtil.getColumnIndexOrThrow(_cursor, "startDate");
          final int _cursorIndexOfIsClosed = CursorUtil.getColumnIndexOrThrow(_cursor, "isClosed");
          final Loan _result;
          if (_cursor.moveToFirst()) {
            final long _tmpId;
            _tmpId = _cursor.getLong(_cursorIndexOfId);
            final long _tmpCustomerId;
            _tmpCustomerId = _cursor.getLong(_cursorIndexOfCustomerId);
            final double _tmpPrincipal;
            _tmpPrincipal = _cursor.getDouble(_cursorIndexOfPrincipal);
            final double _tmpTotalPayable;
            _tmpTotalPayable = _cursor.getDouble(_cursorIndexOfTotalPayable);
            final int _tmpInstallmentCount;
            _tmpInstallmentCount = _cursor.getInt(_cursorIndexOfInstallmentCount);
            final double _tmpInstallmentAmount;
            _tmpInstallmentAmount = _cursor.getDouble(_cursorIndexOfInstallmentAmount);
            final long _tmpStartDate;
            _tmpStartDate = _cursor.getLong(_cursorIndexOfStartDate);
            final boolean _tmpIsClosed;
            final int _tmp;
            _tmp = _cursor.getInt(_cursorIndexOfIsClosed);
            _tmpIsClosed = _tmp != 0;
            _result = new Loan(_tmpId,_tmpCustomerId,_tmpPrincipal,_tmpTotalPayable,_tmpInstallmentCount,_tmpInstallmentAmount,_tmpStartDate,_tmpIsClosed);
          } else {
            _result = null;
          }
          return _result;
        } finally {
          _cursor.close();
          _statement.release();
        }
      }
    }, $completion);
  }

  @Override
  public Flow<List<Loan>> getActiveLoansForLine(final long lineId) {
    final String _sql = "SELECT * FROM loans WHERE customerId IN (SELECT id FROM customers WHERE lineId = ?) AND isClosed = 0";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 1);
    int _argIndex = 1;
    _statement.bindLong(_argIndex, lineId);
    return CoroutinesRoom.createFlow(__db, false, new String[] {"loans",
        "customers"}, new Callable<List<Loan>>() {
      @Override
      @NonNull
      public List<Loan> call() throws Exception {
        final Cursor _cursor = DBUtil.query(__db, _statement, false, null);
        try {
          final int _cursorIndexOfId = CursorUtil.getColumnIndexOrThrow(_cursor, "id");
          final int _cursorIndexOfCustomerId = CursorUtil.getColumnIndexOrThrow(_cursor, "customerId");
          final int _cursorIndexOfPrincipal = CursorUtil.getColumnIndexOrThrow(_cursor, "principal");
          final int _cursorIndexOfTotalPayable = CursorUtil.getColumnIndexOrThrow(_cursor, "totalPayable");
          final int _cursorIndexOfInstallmentCount = CursorUtil.getColumnIndexOrThrow(_cursor, "installmentCount");
          final int _cursorIndexOfInstallmentAmount = CursorUtil.getColumnIndexOrThrow(_cursor, "installmentAmount");
          final int _cursorIndexOfStartDate = CursorUtil.getColumnIndexOrThrow(_cursor, "startDate");
          final int _cursorIndexOfIsClosed = CursorUtil.getColumnIndexOrThrow(_cursor, "isClosed");
          final List<Loan> _result = new ArrayList<Loan>(_cursor.getCount());
          while (_cursor.moveToNext()) {
            final Loan _item;
            final long _tmpId;
            _tmpId = _cursor.getLong(_cursorIndexOfId);
            final long _tmpCustomerId;
            _tmpCustomerId = _cursor.getLong(_cursorIndexOfCustomerId);
            final double _tmpPrincipal;
            _tmpPrincipal = _cursor.getDouble(_cursorIndexOfPrincipal);
            final double _tmpTotalPayable;
            _tmpTotalPayable = _cursor.getDouble(_cursorIndexOfTotalPayable);
            final int _tmpInstallmentCount;
            _tmpInstallmentCount = _cursor.getInt(_cursorIndexOfInstallmentCount);
            final double _tmpInstallmentAmount;
            _tmpInstallmentAmount = _cursor.getDouble(_cursorIndexOfInstallmentAmount);
            final long _tmpStartDate;
            _tmpStartDate = _cursor.getLong(_cursorIndexOfStartDate);
            final boolean _tmpIsClosed;
            final int _tmp;
            _tmp = _cursor.getInt(_cursorIndexOfIsClosed);
            _tmpIsClosed = _tmp != 0;
            _item = new Loan(_tmpId,_tmpCustomerId,_tmpPrincipal,_tmpTotalPayable,_tmpInstallmentCount,_tmpInstallmentAmount,_tmpStartDate,_tmpIsClosed);
            _result.add(_item);
          }
          return _result;
        } finally {
          _cursor.close();
        }
      }

      @Override
      protected void finalize() {
        _statement.release();
      }
    });
  }

  @NonNull
  public static List<Class<?>> getRequiredConverters() {
    return Collections.emptyList();
  }
}
