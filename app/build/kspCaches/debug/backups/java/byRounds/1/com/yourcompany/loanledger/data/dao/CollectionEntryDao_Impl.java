package com.yourcompany.loanledger.data.dao;

import android.database.Cursor;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.room.CoroutinesRoom;
import androidx.room.EntityDeletionOrUpdateAdapter;
import androidx.room.EntityInsertionAdapter;
import androidx.room.RoomDatabase;
import androidx.room.RoomSQLiteQuery;
import androidx.room.SharedSQLiteStatement;
import androidx.room.util.CursorUtil;
import androidx.room.util.DBUtil;
import androidx.sqlite.db.SupportSQLiteStatement;
import com.yourcompany.loanledger.data.entity.CollectionEntry;
import java.lang.Class;
import java.lang.Double;
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
public final class CollectionEntryDao_Impl implements CollectionEntryDao {
  private final RoomDatabase __db;

  private final EntityInsertionAdapter<CollectionEntry> __insertionAdapterOfCollectionEntry;

  private final EntityDeletionOrUpdateAdapter<CollectionEntry> __updateAdapterOfCollectionEntry;

  private final SharedSQLiteStatement __preparedStmtOfMarkPaid;

  public CollectionEntryDao_Impl(@NonNull final RoomDatabase __db) {
    this.__db = __db;
    this.__insertionAdapterOfCollectionEntry = new EntityInsertionAdapter<CollectionEntry>(__db) {
      @Override
      @NonNull
      protected String createQuery() {
        return "INSERT OR ABORT INTO `collection_entries` (`id`,`loanId`,`dueDate`,`installmentIndex`,`amountPaid`,`isPaid`,`paidAt`) VALUES (nullif(?, 0),?,?,?,?,?,?)";
      }

      @Override
      protected void bind(@NonNull final SupportSQLiteStatement statement,
          @NonNull final CollectionEntry entity) {
        statement.bindLong(1, entity.getId());
        statement.bindLong(2, entity.getLoanId());
        statement.bindLong(3, entity.getDueDate());
        statement.bindLong(4, entity.getInstallmentIndex());
        statement.bindDouble(5, entity.getAmountPaid());
        final int _tmp = entity.isPaid() ? 1 : 0;
        statement.bindLong(6, _tmp);
        if (entity.getPaidAt() == null) {
          statement.bindNull(7);
        } else {
          statement.bindLong(7, entity.getPaidAt());
        }
      }
    };
    this.__updateAdapterOfCollectionEntry = new EntityDeletionOrUpdateAdapter<CollectionEntry>(__db) {
      @Override
      @NonNull
      protected String createQuery() {
        return "UPDATE OR ABORT `collection_entries` SET `id` = ?,`loanId` = ?,`dueDate` = ?,`installmentIndex` = ?,`amountPaid` = ?,`isPaid` = ?,`paidAt` = ? WHERE `id` = ?";
      }

      @Override
      protected void bind(@NonNull final SupportSQLiteStatement statement,
          @NonNull final CollectionEntry entity) {
        statement.bindLong(1, entity.getId());
        statement.bindLong(2, entity.getLoanId());
        statement.bindLong(3, entity.getDueDate());
        statement.bindLong(4, entity.getInstallmentIndex());
        statement.bindDouble(5, entity.getAmountPaid());
        final int _tmp = entity.isPaid() ? 1 : 0;
        statement.bindLong(6, _tmp);
        if (entity.getPaidAt() == null) {
          statement.bindNull(7);
        } else {
          statement.bindLong(7, entity.getPaidAt());
        }
        statement.bindLong(8, entity.getId());
      }
    };
    this.__preparedStmtOfMarkPaid = new SharedSQLiteStatement(__db) {
      @Override
      @NonNull
      public String createQuery() {
        final String _query = "UPDATE collection_entries SET amountPaid = ?, isPaid = 1, paidAt = ? WHERE id = ?";
        return _query;
      }
    };
  }

  @Override
  public Object insert(final CollectionEntry entry, final Continuation<? super Long> $completion) {
    return CoroutinesRoom.execute(__db, true, new Callable<Long>() {
      @Override
      @NonNull
      public Long call() throws Exception {
        __db.beginTransaction();
        try {
          final Long _result = __insertionAdapterOfCollectionEntry.insertAndReturnId(entry);
          __db.setTransactionSuccessful();
          return _result;
        } finally {
          __db.endTransaction();
        }
      }
    }, $completion);
  }

  @Override
  public Object update(final CollectionEntry entry, final Continuation<? super Unit> $completion) {
    return CoroutinesRoom.execute(__db, true, new Callable<Unit>() {
      @Override
      @NonNull
      public Unit call() throws Exception {
        __db.beginTransaction();
        try {
          __updateAdapterOfCollectionEntry.handle(entry);
          __db.setTransactionSuccessful();
          return Unit.INSTANCE;
        } finally {
          __db.endTransaction();
        }
      }
    }, $completion);
  }

  @Override
  public Object markPaid(final long id, final double amount, final long paidAt,
      final Continuation<? super Unit> $completion) {
    return CoroutinesRoom.execute(__db, true, new Callable<Unit>() {
      @Override
      @NonNull
      public Unit call() throws Exception {
        final SupportSQLiteStatement _stmt = __preparedStmtOfMarkPaid.acquire();
        int _argIndex = 1;
        _stmt.bindDouble(_argIndex, amount);
        _argIndex = 2;
        _stmt.bindLong(_argIndex, paidAt);
        _argIndex = 3;
        _stmt.bindLong(_argIndex, id);
        try {
          __db.beginTransaction();
          try {
            _stmt.executeUpdateDelete();
            __db.setTransactionSuccessful();
            return Unit.INSTANCE;
          } finally {
            __db.endTransaction();
          }
        } finally {
          __preparedStmtOfMarkPaid.release(_stmt);
        }
      }
    }, $completion);
  }

  @Override
  public Flow<List<CollectionEntry>> getEntriesForDate(final long lineId, final long date) {
    final String _sql = "SELECT * FROM collection_entries WHERE dueDate = ? AND loanId IN (SELECT id FROM loans WHERE customerId IN (SELECT id FROM customers WHERE lineId = ?))";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 2);
    int _argIndex = 1;
    _statement.bindLong(_argIndex, date);
    _argIndex = 2;
    _statement.bindLong(_argIndex, lineId);
    return CoroutinesRoom.createFlow(__db, false, new String[] {"collection_entries", "loans",
        "customers"}, new Callable<List<CollectionEntry>>() {
      @Override
      @NonNull
      public List<CollectionEntry> call() throws Exception {
        final Cursor _cursor = DBUtil.query(__db, _statement, false, null);
        try {
          final int _cursorIndexOfId = CursorUtil.getColumnIndexOrThrow(_cursor, "id");
          final int _cursorIndexOfLoanId = CursorUtil.getColumnIndexOrThrow(_cursor, "loanId");
          final int _cursorIndexOfDueDate = CursorUtil.getColumnIndexOrThrow(_cursor, "dueDate");
          final int _cursorIndexOfInstallmentIndex = CursorUtil.getColumnIndexOrThrow(_cursor, "installmentIndex");
          final int _cursorIndexOfAmountPaid = CursorUtil.getColumnIndexOrThrow(_cursor, "amountPaid");
          final int _cursorIndexOfIsPaid = CursorUtil.getColumnIndexOrThrow(_cursor, "isPaid");
          final int _cursorIndexOfPaidAt = CursorUtil.getColumnIndexOrThrow(_cursor, "paidAt");
          final List<CollectionEntry> _result = new ArrayList<CollectionEntry>(_cursor.getCount());
          while (_cursor.moveToNext()) {
            final CollectionEntry _item;
            final long _tmpId;
            _tmpId = _cursor.getLong(_cursorIndexOfId);
            final long _tmpLoanId;
            _tmpLoanId = _cursor.getLong(_cursorIndexOfLoanId);
            final long _tmpDueDate;
            _tmpDueDate = _cursor.getLong(_cursorIndexOfDueDate);
            final int _tmpInstallmentIndex;
            _tmpInstallmentIndex = _cursor.getInt(_cursorIndexOfInstallmentIndex);
            final double _tmpAmountPaid;
            _tmpAmountPaid = _cursor.getDouble(_cursorIndexOfAmountPaid);
            final boolean _tmpIsPaid;
            final int _tmp;
            _tmp = _cursor.getInt(_cursorIndexOfIsPaid);
            _tmpIsPaid = _tmp != 0;
            final Long _tmpPaidAt;
            if (_cursor.isNull(_cursorIndexOfPaidAt)) {
              _tmpPaidAt = null;
            } else {
              _tmpPaidAt = _cursor.getLong(_cursorIndexOfPaidAt);
            }
            _item = new CollectionEntry(_tmpId,_tmpLoanId,_tmpDueDate,_tmpInstallmentIndex,_tmpAmountPaid,_tmpIsPaid,_tmpPaidAt);
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

  @Override
  public Flow<List<CollectionEntry>> getPendingCollections(final long lineId, final long today) {
    final String _sql = "SELECT * FROM collection_entries WHERE isPaid = 0 AND dueDate <= ? AND loanId IN (SELECT id FROM loans WHERE customerId IN (SELECT id FROM customers WHERE lineId = ?)) ORDER BY dueDate ASC";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 2);
    int _argIndex = 1;
    _statement.bindLong(_argIndex, today);
    _argIndex = 2;
    _statement.bindLong(_argIndex, lineId);
    return CoroutinesRoom.createFlow(__db, false, new String[] {"collection_entries", "loans",
        "customers"}, new Callable<List<CollectionEntry>>() {
      @Override
      @NonNull
      public List<CollectionEntry> call() throws Exception {
        final Cursor _cursor = DBUtil.query(__db, _statement, false, null);
        try {
          final int _cursorIndexOfId = CursorUtil.getColumnIndexOrThrow(_cursor, "id");
          final int _cursorIndexOfLoanId = CursorUtil.getColumnIndexOrThrow(_cursor, "loanId");
          final int _cursorIndexOfDueDate = CursorUtil.getColumnIndexOrThrow(_cursor, "dueDate");
          final int _cursorIndexOfInstallmentIndex = CursorUtil.getColumnIndexOrThrow(_cursor, "installmentIndex");
          final int _cursorIndexOfAmountPaid = CursorUtil.getColumnIndexOrThrow(_cursor, "amountPaid");
          final int _cursorIndexOfIsPaid = CursorUtil.getColumnIndexOrThrow(_cursor, "isPaid");
          final int _cursorIndexOfPaidAt = CursorUtil.getColumnIndexOrThrow(_cursor, "paidAt");
          final List<CollectionEntry> _result = new ArrayList<CollectionEntry>(_cursor.getCount());
          while (_cursor.moveToNext()) {
            final CollectionEntry _item;
            final long _tmpId;
            _tmpId = _cursor.getLong(_cursorIndexOfId);
            final long _tmpLoanId;
            _tmpLoanId = _cursor.getLong(_cursorIndexOfLoanId);
            final long _tmpDueDate;
            _tmpDueDate = _cursor.getLong(_cursorIndexOfDueDate);
            final int _tmpInstallmentIndex;
            _tmpInstallmentIndex = _cursor.getInt(_cursorIndexOfInstallmentIndex);
            final double _tmpAmountPaid;
            _tmpAmountPaid = _cursor.getDouble(_cursorIndexOfAmountPaid);
            final boolean _tmpIsPaid;
            final int _tmp;
            _tmp = _cursor.getInt(_cursorIndexOfIsPaid);
            _tmpIsPaid = _tmp != 0;
            final Long _tmpPaidAt;
            if (_cursor.isNull(_cursorIndexOfPaidAt)) {
              _tmpPaidAt = null;
            } else {
              _tmpPaidAt = _cursor.getLong(_cursorIndexOfPaidAt);
            }
            _item = new CollectionEntry(_tmpId,_tmpLoanId,_tmpDueDate,_tmpInstallmentIndex,_tmpAmountPaid,_tmpIsPaid,_tmpPaidAt);
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

  @Override
  public Flow<Double> getTodayCollectionTotal(final long lineId, final long date) {
    final String _sql = "SELECT SUM(amountPaid) FROM collection_entries WHERE dueDate = ? AND loanId IN (SELECT id FROM loans WHERE customerId IN (SELECT id FROM customers WHERE lineId = ?))";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 2);
    int _argIndex = 1;
    _statement.bindLong(_argIndex, date);
    _argIndex = 2;
    _statement.bindLong(_argIndex, lineId);
    return CoroutinesRoom.createFlow(__db, false, new String[] {"collection_entries", "loans",
        "customers"}, new Callable<Double>() {
      @Override
      @Nullable
      public Double call() throws Exception {
        final Cursor _cursor = DBUtil.query(__db, _statement, false, null);
        try {
          final Double _result;
          if (_cursor.moveToFirst()) {
            final Double _tmp;
            if (_cursor.isNull(0)) {
              _tmp = null;
            } else {
              _tmp = _cursor.getDouble(0);
            }
            _result = _tmp;
          } else {
            _result = null;
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
