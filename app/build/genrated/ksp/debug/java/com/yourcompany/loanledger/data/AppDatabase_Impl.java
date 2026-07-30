package com.yourcompany.loanledger.data;

import androidx.annotation.NonNull;
import androidx.room.DatabaseConfiguration;
import androidx.room.InvalidationTracker;
import androidx.room.RoomDatabase;
import androidx.room.RoomOpenHelper;
import androidx.room.migration.AutoMigrationSpec;
import androidx.room.migration.Migration;
import androidx.room.util.DBUtil;
import androidx.room.util.TableInfo;
import androidx.sqlite.db.SupportSQLiteDatabase;
import androidx.sqlite.db.SupportSQLiteOpenHelper;
import com.yourcompany.loanledger.data.dao.CollectionEntryDao;
import com.yourcompany.loanledger.data.dao.CollectionEntryDao_Impl;
import com.yourcompany.loanledger.data.dao.CustomerDao;
import com.yourcompany.loanledger.data.dao.CustomerDao_Impl;
import com.yourcompany.loanledger.data.dao.ExpenseDao;
import com.yourcompany.loanledger.data.dao.ExpenseDao_Impl;
import com.yourcompany.loanledger.data.dao.LineDao;
import com.yourcompany.loanledger.data.dao.LineDao_Impl;
import com.yourcompany.loanledger.data.dao.LoanDao;
import com.yourcompany.loanledger.data.dao.LoanDao_Impl;
import java.lang.Class;
import java.lang.Override;
import java.lang.String;
import java.lang.SuppressWarnings;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import javax.annotation.processing.Generated;

@Generated("androidx.room.RoomProcessor")
@SuppressWarnings({"unchecked", "deprecation"})
public final class AppDatabase_Impl extends AppDatabase {
  private volatile LineDao _lineDao;

  private volatile CustomerDao _customerDao;

  private volatile LoanDao _loanDao;

  private volatile CollectionEntryDao _collectionEntryDao;

  private volatile ExpenseDao _expenseDao;

  @Override
  @NonNull
  protected SupportSQLiteOpenHelper createOpenHelper(@NonNull final DatabaseConfiguration config) {
    final SupportSQLiteOpenHelper.Callback _openCallback = new RoomOpenHelper(config, new RoomOpenHelper.Delegate(1) {
      @Override
      public void createAllTables(@NonNull final SupportSQLiteDatabase db) {
        db.execSQL("CREATE TABLE IF NOT EXISTS `lines` (`id` INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL, `name` TEXT NOT NULL, `type` TEXT NOT NULL, `investment` REAL NOT NULL, `createdAt` INTEGER NOT NULL)");
        db.execSQL("CREATE TABLE IF NOT EXISTS `customers` (`id` INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL, `lineId` INTEGER NOT NULL, `name` TEXT NOT NULL, `phone` TEXT, `address` TEXT, `isActive` INTEGER NOT NULL, `sortOrder` INTEGER NOT NULL, FOREIGN KEY(`lineId`) REFERENCES `lines`(`id`) ON UPDATE NO ACTION ON DELETE CASCADE )");
        db.execSQL("CREATE TABLE IF NOT EXISTS `loans` (`id` INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL, `customerId` INTEGER NOT NULL, `principal` REAL NOT NULL, `totalPayable` REAL NOT NULL, `installmentCount` INTEGER NOT NULL, `installmentAmount` REAL NOT NULL, `startDate` INTEGER NOT NULL, `isClosed` INTEGER NOT NULL, FOREIGN KEY(`customerId`) REFERENCES `customers`(`id`) ON UPDATE NO ACTION ON DELETE CASCADE )");
        db.execSQL("CREATE TABLE IF NOT EXISTS `collection_entries` (`id` INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL, `loanId` INTEGER NOT NULL, `dueDate` INTEGER NOT NULL, `installmentIndex` INTEGER NOT NULL, `amountPaid` REAL NOT NULL, `isPaid` INTEGER NOT NULL, `paidAt` INTEGER, FOREIGN KEY(`loanId`) REFERENCES `loans`(`id`) ON UPDATE NO ACTION ON DELETE CASCADE )");
        db.execSQL("CREATE TABLE IF NOT EXISTS `expenses` (`id` INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL, `lineId` INTEGER NOT NULL, `expenseTypeId` INTEGER NOT NULL, `amount` REAL NOT NULL, `note` TEXT, `date` INTEGER NOT NULL)");
        db.execSQL("CREATE TABLE IF NOT EXISTS `expense_types` (`id` INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL, `name` TEXT NOT NULL)");
        db.execSQL("CREATE TABLE IF NOT EXISTS `areas` (`id` INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL, `name` TEXT NOT NULL)");
        db.execSQL("CREATE TABLE IF NOT EXISTS room_master_table (id INTEGER PRIMARY KEY,identity_hash TEXT)");
        db.execSQL("INSERT OR REPLACE INTO room_master_table (id,identity_hash) VALUES(42, '12071a312e65a529c51dcb237fbf6288')");
      }

      @Override
      public void dropAllTables(@NonNull final SupportSQLiteDatabase db) {
        db.execSQL("DROP TABLE IF EXISTS `lines`");
        db.execSQL("DROP TABLE IF EXISTS `customers`");
        db.execSQL("DROP TABLE IF EXISTS `loans`");
        db.execSQL("DROP TABLE IF EXISTS `collection_entries`");
        db.execSQL("DROP TABLE IF EXISTS `expenses`");
        db.execSQL("DROP TABLE IF EXISTS `expense_types`");
        db.execSQL("DROP TABLE IF EXISTS `areas`");
        final List<? extends RoomDatabase.Callback> _callbacks = mCallbacks;
        if (_callbacks != null) {
          for (RoomDatabase.Callback _callback : _callbacks) {
            _callback.onDestructiveMigration(db);
          }
        }
      }

      @Override
      public void onCreate(@NonNull final SupportSQLiteDatabase db) {
        final List<? extends RoomDatabase.Callback> _callbacks = mCallbacks;
        if (_callbacks != null) {
          for (RoomDatabase.Callback _callback : _callbacks) {
            _callback.onCreate(db);
          }
        }
      }

      @Override
      public void onOpen(@NonNull final SupportSQLiteDatabase db) {
        mDatabase = db;
        db.execSQL("PRAGMA foreign_keys = ON");
        internalInitInvalidationTracker(db);
        final List<? extends RoomDatabase.Callback> _callbacks = mCallbacks;
        if (_callbacks != null) {
          for (RoomDatabase.Callback _callback : _callbacks) {
            _callback.onOpen(db);
          }
        }
      }

      @Override
      public void onPreMigrate(@NonNull final SupportSQLiteDatabase db) {
        DBUtil.dropFtsSyncTriggers(db);
      }

      @Override
      public void onPostMigrate(@NonNull final SupportSQLiteDatabase db) {
      }

      @Override
      @NonNull
      public RoomOpenHelper.ValidationResult onValidateSchema(
          @NonNull final SupportSQLiteDatabase db) {
        final HashMap<String, TableInfo.Column> _columnsLines = new HashMap<String, TableInfo.Column>(5);
        _columnsLines.put("id", new TableInfo.Column("id", "INTEGER", true, 1, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsLines.put("name", new TableInfo.Column("name", "TEXT", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsLines.put("type", new TableInfo.Column("type", "TEXT", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsLines.put("investment", new TableInfo.Column("investment", "REAL", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsLines.put("createdAt", new TableInfo.Column("createdAt", "INTEGER", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        final HashSet<TableInfo.ForeignKey> _foreignKeysLines = new HashSet<TableInfo.ForeignKey>(0);
        final HashSet<TableInfo.Index> _indicesLines = new HashSet<TableInfo.Index>(0);
        final TableInfo _infoLines = new TableInfo("lines", _columnsLines, _foreignKeysLines, _indicesLines);
        final TableInfo _existingLines = TableInfo.read(db, "lines");
        if (!_infoLines.equals(_existingLines)) {
          return new RoomOpenHelper.ValidationResult(false, "lines(com.yourcompany.loanledger.data.entity.Line).\n"
                  + " Expected:\n" + _infoLines + "\n"
                  + " Found:\n" + _existingLines);
        }
        final HashMap<String, TableInfo.Column> _columnsCustomers = new HashMap<String, TableInfo.Column>(7);
        _columnsCustomers.put("id", new TableInfo.Column("id", "INTEGER", true, 1, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsCustomers.put("lineId", new TableInfo.Column("lineId", "INTEGER", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsCustomers.put("name", new TableInfo.Column("name", "TEXT", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsCustomers.put("phone", new TableInfo.Column("phone", "TEXT", false, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsCustomers.put("address", new TableInfo.Column("address", "TEXT", false, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsCustomers.put("isActive", new TableInfo.Column("isActive", "INTEGER", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsCustomers.put("sortOrder", new TableInfo.Column("sortOrder", "INTEGER", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        final HashSet<TableInfo.ForeignKey> _foreignKeysCustomers = new HashSet<TableInfo.ForeignKey>(1);
        _foreignKeysCustomers.add(new TableInfo.ForeignKey("lines", "CASCADE", "NO ACTION", Arrays.asList("lineId"), Arrays.asList("id")));
        final HashSet<TableInfo.Index> _indicesCustomers = new HashSet<TableInfo.Index>(0);
        final TableInfo _infoCustomers = new TableInfo("customers", _columnsCustomers, _foreignKeysCustomers, _indicesCustomers);
        final TableInfo _existingCustomers = TableInfo.read(db, "customers");
        if (!_infoCustomers.equals(_existingCustomers)) {
          return new RoomOpenHelper.ValidationResult(false, "customers(com.yourcompany.loanledger.data.entity.Customer).\n"
                  + " Expected:\n" + _infoCustomers + "\n"
                  + " Found:\n" + _existingCustomers);
        }
        final HashMap<String, TableInfo.Column> _columnsLoans = new HashMap<String, TableInfo.Column>(8);
        _columnsLoans.put("id", new TableInfo.Column("id", "INTEGER", true, 1, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsLoans.put("customerId", new TableInfo.Column("customerId", "INTEGER", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsLoans.put("principal", new TableInfo.Column("principal", "REAL", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsLoans.put("totalPayable", new TableInfo.Column("totalPayable", "REAL", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsLoans.put("installmentCount", new TableInfo.Column("installmentCount", "INTEGER", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsLoans.put("installmentAmount", new TableInfo.Column("installmentAmount", "REAL", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsLoans.put("startDate", new TableInfo.Column("startDate", "INTEGER", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsLoans.put("isClosed", new TableInfo.Column("isClosed", "INTEGER", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        final HashSet<TableInfo.ForeignKey> _foreignKeysLoans = new HashSet<TableInfo.ForeignKey>(1);
        _foreignKeysLoans.add(new TableInfo.ForeignKey("customers", "CASCADE", "NO ACTION", Arrays.asList("customerId"), Arrays.asList("id")));
        final HashSet<TableInfo.Index> _indicesLoans = new HashSet<TableInfo.Index>(0);
        final TableInfo _infoLoans = new TableInfo("loans", _columnsLoans, _foreignKeysLoans, _indicesLoans);
        final TableInfo _existingLoans = TableInfo.read(db, "loans");
        if (!_infoLoans.equals(_existingLoans)) {
          return new RoomOpenHelper.ValidationResult(false, "loans(com.yourcompany.loanledger.data.entity.Loan).\n"
                  + " Expected:\n" + _infoLoans + "\n"
                  + " Found:\n" + _existingLoans);
        }
        final HashMap<String, TableInfo.Column> _columnsCollectionEntries = new HashMap<String, TableInfo.Column>(7);
        _columnsCollectionEntries.put("id", new TableInfo.Column("id", "INTEGER", true, 1, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsCollectionEntries.put("loanId", new TableInfo.Column("loanId", "INTEGER", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsCollectionEntries.put("dueDate", new TableInfo.Column("dueDate", "INTEGER", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsCollectionEntries.put("installmentIndex", new TableInfo.Column("installmentIndex", "INTEGER", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsCollectionEntries.put("amountPaid", new TableInfo.Column("amountPaid", "REAL", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsCollectionEntries.put("isPaid", new TableInfo.Column("isPaid", "INTEGER", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsCollectionEntries.put("paidAt", new TableInfo.Column("paidAt", "INTEGER", false, 0, null, TableInfo.CREATED_FROM_ENTITY));
        final HashSet<TableInfo.ForeignKey> _foreignKeysCollectionEntries = new HashSet<TableInfo.ForeignKey>(1);
        _foreignKeysCollectionEntries.add(new TableInfo.ForeignKey("loans", "CASCADE", "NO ACTION", Arrays.asList("loanId"), Arrays.asList("id")));
        final HashSet<TableInfo.Index> _indicesCollectionEntries = new HashSet<TableInfo.Index>(0);
        final TableInfo _infoCollectionEntries = new TableInfo("collection_entries", _columnsCollectionEntries, _foreignKeysCollectionEntries, _indicesCollectionEntries);
        final TableInfo _existingCollectionEntries = TableInfo.read(db, "collection_entries");
        if (!_infoCollectionEntries.equals(_existingCollectionEntries)) {
          return new RoomOpenHelper.ValidationResult(false, "collection_entries(com.yourcompany.loanledger.data.entity.CollectionEntry).\n"
                  + " Expected:\n" + _infoCollectionEntries + "\n"
                  + " Found:\n" + _existingCollectionEntries);
        }
        final HashMap<String, TableInfo.Column> _columnsExpenses = new HashMap<String, TableInfo.Column>(6);
        _columnsExpenses.put("id", new TableInfo.Column("id", "INTEGER", true, 1, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsExpenses.put("lineId", new TableInfo.Column("lineId", "INTEGER", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsExpenses.put("expenseTypeId", new TableInfo.Column("expenseTypeId", "INTEGER", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsExpenses.put("amount", new TableInfo.Column("amount", "REAL", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsExpenses.put("note", new TableInfo.Column("note", "TEXT", false, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsExpenses.put("date", new TableInfo.Column("date", "INTEGER", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        final HashSet<TableInfo.ForeignKey> _foreignKeysExpenses = new HashSet<TableInfo.ForeignKey>(0);
        final HashSet<TableInfo.Index> _indicesExpenses = new HashSet<TableInfo.Index>(0);
        final TableInfo _infoExpenses = new TableInfo("expenses", _columnsExpenses, _foreignKeysExpenses, _indicesExpenses);
        final TableInfo _existingExpenses = TableInfo.read(db, "expenses");
        if (!_infoExpenses.equals(_existingExpenses)) {
          return new RoomOpenHelper.ValidationResult(false, "expenses(com.yourcompany.loanledger.data.entity.Expense).\n"
                  + " Expected:\n" + _infoExpenses + "\n"
                  + " Found:\n" + _existingExpenses);
        }
        final HashMap<String, TableInfo.Column> _columnsExpenseTypes = new HashMap<String, TableInfo.Column>(2);
        _columnsExpenseTypes.put("id", new TableInfo.Column("id", "INTEGER", true, 1, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsExpenseTypes.put("name", new TableInfo.Column("name", "TEXT", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        final HashSet<TableInfo.ForeignKey> _foreignKeysExpenseTypes = new HashSet<TableInfo.ForeignKey>(0);
        final HashSet<TableInfo.Index> _indicesExpenseTypes = new HashSet<TableInfo.Index>(0);
        final TableInfo _infoExpenseTypes = new TableInfo("expense_types", _columnsExpenseTypes, _foreignKeysExpenseTypes, _indicesExpenseTypes);
        final TableInfo _existingExpenseTypes = TableInfo.read(db, "expense_types");
        if (!_infoExpenseTypes.equals(_existingExpenseTypes)) {
          return new RoomOpenHelper.ValidationResult(false, "expense_types(com.yourcompany.loanledger.data.entity.ExpenseType).\n"
                  + " Expected:\n" + _infoExpenseTypes + "\n"
                  + " Found:\n" + _existingExpenseTypes);
        }
        final HashMap<String, TableInfo.Column> _columnsAreas = new HashMap<String, TableInfo.Column>(2);
        _columnsAreas.put("id", new TableInfo.Column("id", "INTEGER", true, 1, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsAreas.put("name", new TableInfo.Column("name", "TEXT", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        final HashSet<TableInfo.ForeignKey> _foreignKeysAreas = new HashSet<TableInfo.ForeignKey>(0);
        final HashSet<TableInfo.Index> _indicesAreas = new HashSet<TableInfo.Index>(0);
        final TableInfo _infoAreas = new TableInfo("areas", _columnsAreas, _foreignKeysAreas, _indicesAreas);
        final TableInfo _existingAreas = TableInfo.read(db, "areas");
        if (!_infoAreas.equals(_existingAreas)) {
          return new RoomOpenHelper.ValidationResult(false, "areas(com.yourcompany.loanledger.data.entity.Area).\n"
                  + " Expected:\n" + _infoAreas + "\n"
                  + " Found:\n" + _existingAreas);
        }
        return new RoomOpenHelper.ValidationResult(true, null);
      }
    }, "12071a312e65a529c51dcb237fbf6288", "de1201861321e8404051ca618c6ecb8f");
    final SupportSQLiteOpenHelper.Configuration _sqliteConfig = SupportSQLiteOpenHelper.Configuration.builder(config.context).name(config.name).callback(_openCallback).build();
    final SupportSQLiteOpenHelper _helper = config.sqliteOpenHelperFactory.create(_sqliteConfig);
    return _helper;
  }

  @Override
  @NonNull
  protected InvalidationTracker createInvalidationTracker() {
    final HashMap<String, String> _shadowTablesMap = new HashMap<String, String>(0);
    final HashMap<String, Set<String>> _viewTables = new HashMap<String, Set<String>>(0);
    return new InvalidationTracker(this, _shadowTablesMap, _viewTables, "lines","customers","loans","collection_entries","expenses","expense_types","areas");
  }

  @Override
  public void clearAllTables() {
    super.assertNotMainThread();
    final SupportSQLiteDatabase _db = super.getOpenHelper().getWritableDatabase();
    final boolean _supportsDeferForeignKeys = android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.LOLLIPOP;
    try {
      if (!_supportsDeferForeignKeys) {
        _db.execSQL("PRAGMA foreign_keys = FALSE");
      }
      super.beginTransaction();
      if (_supportsDeferForeignKeys) {
        _db.execSQL("PRAGMA defer_foreign_keys = TRUE");
      }
      _db.execSQL("DELETE FROM `lines`");
      _db.execSQL("DELETE FROM `customers`");
      _db.execSQL("DELETE FROM `loans`");
      _db.execSQL("DELETE FROM `collection_entries`");
      _db.execSQL("DELETE FROM `expenses`");
      _db.execSQL("DELETE FROM `expense_types`");
      _db.execSQL("DELETE FROM `areas`");
      super.setTransactionSuccessful();
    } finally {
      super.endTransaction();
      if (!_supportsDeferForeignKeys) {
        _db.execSQL("PRAGMA foreign_keys = TRUE");
      }
      _db.query("PRAGMA wal_checkpoint(FULL)").close();
      if (!_db.inTransaction()) {
        _db.execSQL("VACUUM");
      }
    }
  }

  @Override
  @NonNull
  protected Map<Class<?>, List<Class<?>>> getRequiredTypeConverters() {
    final HashMap<Class<?>, List<Class<?>>> _typeConvertersMap = new HashMap<Class<?>, List<Class<?>>>();
    _typeConvertersMap.put(LineDao.class, LineDao_Impl.getRequiredConverters());
    _typeConvertersMap.put(CustomerDao.class, CustomerDao_Impl.getRequiredConverters());
    _typeConvertersMap.put(LoanDao.class, LoanDao_Impl.getRequiredConverters());
    _typeConvertersMap.put(CollectionEntryDao.class, CollectionEntryDao_Impl.getRequiredConverters());
    _typeConvertersMap.put(ExpenseDao.class, ExpenseDao_Impl.getRequiredConverters());
    return _typeConvertersMap;
  }

  @Override
  @NonNull
  public Set<Class<? extends AutoMigrationSpec>> getRequiredAutoMigrationSpecs() {
    final HashSet<Class<? extends AutoMigrationSpec>> _autoMigrationSpecsSet = new HashSet<Class<? extends AutoMigrationSpec>>();
    return _autoMigrationSpecsSet;
  }

  @Override
  @NonNull
  public List<Migration> getAutoMigrations(
      @NonNull final Map<Class<? extends AutoMigrationSpec>, AutoMigrationSpec> autoMigrationSpecs) {
    final List<Migration> _autoMigrations = new ArrayList<Migration>();
    return _autoMigrations;
  }

  @Override
  public LineDao lineDao() {
    if (_lineDao != null) {
      return _lineDao;
    } else {
      synchronized(this) {
        if(_lineDao == null) {
          _lineDao = new LineDao_Impl(this);
        }
        return _lineDao;
      }
    }
  }

  @Override
  public CustomerDao customerDao() {
    if (_customerDao != null) {
      return _customerDao;
    } else {
      synchronized(this) {
        if(_customerDao == null) {
          _customerDao = new CustomerDao_Impl(this);
        }
        return _customerDao;
      }
    }
  }

  @Override
  public LoanDao loanDao() {
    if (_loanDao != null) {
      return _loanDao;
    } else {
      synchronized(this) {
        if(_loanDao == null) {
          _loanDao = new LoanDao_Impl(this);
        }
        return _loanDao;
      }
    }
  }

  @Override
  public CollectionEntryDao collectionEntryDao() {
    if (_collectionEntryDao != null) {
      return _collectionEntryDao;
    } else {
      synchronized(this) {
        if(_collectionEntryDao == null) {
          _collectionEntryDao = new CollectionEntryDao_Impl(this);
        }
        return _collectionEntryDao;
      }
    }
  }

  @Override
  public ExpenseDao expenseDao() {
    if (_expenseDao != null) {
      return _expenseDao;
    } else {
      synchronized(this) {
        if(_expenseDao == null) {
          _expenseDao = new ExpenseDao_Impl(this);
        }
        return _expenseDao;
      }
    }
  }
}
