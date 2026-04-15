package com.project189.data.local;

import android.database.Cursor;
import android.os.CancellationSignal;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.lifecycle.LiveData;
import androidx.room.CoroutinesRoom;
import androidx.room.EntityDeletionOrUpdateAdapter;
import androidx.room.EntityInsertionAdapter;
import androidx.room.RoomDatabase;
import androidx.room.RoomSQLiteQuery;
import androidx.room.util.CursorUtil;
import androidx.room.util.DBUtil;
import androidx.sqlite.db.SupportSQLiteStatement;
import java.lang.Boolean;
import java.lang.Class;
import java.lang.Exception;
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

@Generated("androidx.room.RoomProcessor")
@SuppressWarnings({"unchecked", "deprecation"})
public final class FavoriteDao_Impl implements FavoriteDao {
  private final RoomDatabase __db;

  private final EntityInsertionAdapter<FavoriteEntity> __insertionAdapterOfFavoriteEntity;

  private final EntityDeletionOrUpdateAdapter<FavoriteEntity> __deletionAdapterOfFavoriteEntity;

  public FavoriteDao_Impl(@NonNull final RoomDatabase __db) {
    this.__db = __db;
    this.__insertionAdapterOfFavoriteEntity = new EntityInsertionAdapter<FavoriteEntity>(__db) {
      @Override
      @NonNull
      protected String createQuery() {
        return "INSERT OR REPLACE INTO `favorites` (`title`,`address`,`pic`,`price`,`score`,`distance`,`duration`,`bed`,`dateTour`,`timeTour`,`description`,`tourGuideName`,`tourGuidePhone`,`tourGuidePic`) VALUES (?,?,?,?,?,?,?,?,?,?,?,?,?,?)";
      }

      @Override
      protected void bind(@NonNull final SupportSQLiteStatement statement,
          @NonNull final FavoriteEntity entity) {
        statement.bindString(1, entity.getTitle());
        statement.bindString(2, entity.getAddress());
        statement.bindString(3, entity.getPic());
        statement.bindDouble(4, entity.getPrice());
        statement.bindDouble(5, entity.getScore());
        statement.bindString(6, entity.getDistance());
        statement.bindString(7, entity.getDuration());
        statement.bindLong(8, entity.getBed());
        statement.bindString(9, entity.getDateTour());
        statement.bindString(10, entity.getTimeTour());
        statement.bindString(11, entity.getDescription());
        statement.bindString(12, entity.getTourGuideName());
        statement.bindString(13, entity.getTourGuidePhone());
        statement.bindString(14, entity.getTourGuidePic());
      }
    };
    this.__deletionAdapterOfFavoriteEntity = new EntityDeletionOrUpdateAdapter<FavoriteEntity>(__db) {
      @Override
      @NonNull
      protected String createQuery() {
        return "DELETE FROM `favorites` WHERE `title` = ?";
      }

      @Override
      protected void bind(@NonNull final SupportSQLiteStatement statement,
          @NonNull final FavoriteEntity entity) {
        statement.bindString(1, entity.getTitle());
      }
    };
  }

  @Override
  public Object insertFavorite(final FavoriteEntity favorite,
      final Continuation<? super Unit> $completion) {
    return CoroutinesRoom.execute(__db, true, new Callable<Unit>() {
      @Override
      @NonNull
      public Unit call() throws Exception {
        __db.beginTransaction();
        try {
          __insertionAdapterOfFavoriteEntity.insert(favorite);
          __db.setTransactionSuccessful();
          return Unit.INSTANCE;
        } finally {
          __db.endTransaction();
        }
      }
    }, $completion);
  }

  @Override
  public Object deleteFavorite(final FavoriteEntity favorite,
      final Continuation<? super Unit> $completion) {
    return CoroutinesRoom.execute(__db, true, new Callable<Unit>() {
      @Override
      @NonNull
      public Unit call() throws Exception {
        __db.beginTransaction();
        try {
          __deletionAdapterOfFavoriteEntity.handle(favorite);
          __db.setTransactionSuccessful();
          return Unit.INSTANCE;
        } finally {
          __db.endTransaction();
        }
      }
    }, $completion);
  }

  @Override
  public LiveData<List<FavoriteEntity>> getAllFavorites() {
    final String _sql = "SELECT * FROM favorites";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 0);
    return __db.getInvalidationTracker().createLiveData(new String[] {"favorites"}, false, new Callable<List<FavoriteEntity>>() {
      @Override
      @Nullable
      public List<FavoriteEntity> call() throws Exception {
        final Cursor _cursor = DBUtil.query(__db, _statement, false, null);
        try {
          final int _cursorIndexOfTitle = CursorUtil.getColumnIndexOrThrow(_cursor, "title");
          final int _cursorIndexOfAddress = CursorUtil.getColumnIndexOrThrow(_cursor, "address");
          final int _cursorIndexOfPic = CursorUtil.getColumnIndexOrThrow(_cursor, "pic");
          final int _cursorIndexOfPrice = CursorUtil.getColumnIndexOrThrow(_cursor, "price");
          final int _cursorIndexOfScore = CursorUtil.getColumnIndexOrThrow(_cursor, "score");
          final int _cursorIndexOfDistance = CursorUtil.getColumnIndexOrThrow(_cursor, "distance");
          final int _cursorIndexOfDuration = CursorUtil.getColumnIndexOrThrow(_cursor, "duration");
          final int _cursorIndexOfBed = CursorUtil.getColumnIndexOrThrow(_cursor, "bed");
          final int _cursorIndexOfDateTour = CursorUtil.getColumnIndexOrThrow(_cursor, "dateTour");
          final int _cursorIndexOfTimeTour = CursorUtil.getColumnIndexOrThrow(_cursor, "timeTour");
          final int _cursorIndexOfDescription = CursorUtil.getColumnIndexOrThrow(_cursor, "description");
          final int _cursorIndexOfTourGuideName = CursorUtil.getColumnIndexOrThrow(_cursor, "tourGuideName");
          final int _cursorIndexOfTourGuidePhone = CursorUtil.getColumnIndexOrThrow(_cursor, "tourGuidePhone");
          final int _cursorIndexOfTourGuidePic = CursorUtil.getColumnIndexOrThrow(_cursor, "tourGuidePic");
          final List<FavoriteEntity> _result = new ArrayList<FavoriteEntity>(_cursor.getCount());
          while (_cursor.moveToNext()) {
            final FavoriteEntity _item;
            final String _tmpTitle;
            _tmpTitle = _cursor.getString(_cursorIndexOfTitle);
            final String _tmpAddress;
            _tmpAddress = _cursor.getString(_cursorIndexOfAddress);
            final String _tmpPic;
            _tmpPic = _cursor.getString(_cursorIndexOfPic);
            final double _tmpPrice;
            _tmpPrice = _cursor.getDouble(_cursorIndexOfPrice);
            final double _tmpScore;
            _tmpScore = _cursor.getDouble(_cursorIndexOfScore);
            final String _tmpDistance;
            _tmpDistance = _cursor.getString(_cursorIndexOfDistance);
            final String _tmpDuration;
            _tmpDuration = _cursor.getString(_cursorIndexOfDuration);
            final int _tmpBed;
            _tmpBed = _cursor.getInt(_cursorIndexOfBed);
            final String _tmpDateTour;
            _tmpDateTour = _cursor.getString(_cursorIndexOfDateTour);
            final String _tmpTimeTour;
            _tmpTimeTour = _cursor.getString(_cursorIndexOfTimeTour);
            final String _tmpDescription;
            _tmpDescription = _cursor.getString(_cursorIndexOfDescription);
            final String _tmpTourGuideName;
            _tmpTourGuideName = _cursor.getString(_cursorIndexOfTourGuideName);
            final String _tmpTourGuidePhone;
            _tmpTourGuidePhone = _cursor.getString(_cursorIndexOfTourGuidePhone);
            final String _tmpTourGuidePic;
            _tmpTourGuidePic = _cursor.getString(_cursorIndexOfTourGuidePic);
            _item = new FavoriteEntity(_tmpTitle,_tmpAddress,_tmpPic,_tmpPrice,_tmpScore,_tmpDistance,_tmpDuration,_tmpBed,_tmpDateTour,_tmpTimeTour,_tmpDescription,_tmpTourGuideName,_tmpTourGuidePhone,_tmpTourGuidePic);
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
  public Object getFavoriteByTitle(final String title,
      final Continuation<? super FavoriteEntity> $completion) {
    final String _sql = "SELECT * FROM favorites WHERE title = ? LIMIT 1";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 1);
    int _argIndex = 1;
    _statement.bindString(_argIndex, title);
    final CancellationSignal _cancellationSignal = DBUtil.createCancellationSignal();
    return CoroutinesRoom.execute(__db, false, _cancellationSignal, new Callable<FavoriteEntity>() {
      @Override
      @Nullable
      public FavoriteEntity call() throws Exception {
        final Cursor _cursor = DBUtil.query(__db, _statement, false, null);
        try {
          final int _cursorIndexOfTitle = CursorUtil.getColumnIndexOrThrow(_cursor, "title");
          final int _cursorIndexOfAddress = CursorUtil.getColumnIndexOrThrow(_cursor, "address");
          final int _cursorIndexOfPic = CursorUtil.getColumnIndexOrThrow(_cursor, "pic");
          final int _cursorIndexOfPrice = CursorUtil.getColumnIndexOrThrow(_cursor, "price");
          final int _cursorIndexOfScore = CursorUtil.getColumnIndexOrThrow(_cursor, "score");
          final int _cursorIndexOfDistance = CursorUtil.getColumnIndexOrThrow(_cursor, "distance");
          final int _cursorIndexOfDuration = CursorUtil.getColumnIndexOrThrow(_cursor, "duration");
          final int _cursorIndexOfBed = CursorUtil.getColumnIndexOrThrow(_cursor, "bed");
          final int _cursorIndexOfDateTour = CursorUtil.getColumnIndexOrThrow(_cursor, "dateTour");
          final int _cursorIndexOfTimeTour = CursorUtil.getColumnIndexOrThrow(_cursor, "timeTour");
          final int _cursorIndexOfDescription = CursorUtil.getColumnIndexOrThrow(_cursor, "description");
          final int _cursorIndexOfTourGuideName = CursorUtil.getColumnIndexOrThrow(_cursor, "tourGuideName");
          final int _cursorIndexOfTourGuidePhone = CursorUtil.getColumnIndexOrThrow(_cursor, "tourGuidePhone");
          final int _cursorIndexOfTourGuidePic = CursorUtil.getColumnIndexOrThrow(_cursor, "tourGuidePic");
          final FavoriteEntity _result;
          if (_cursor.moveToFirst()) {
            final String _tmpTitle;
            _tmpTitle = _cursor.getString(_cursorIndexOfTitle);
            final String _tmpAddress;
            _tmpAddress = _cursor.getString(_cursorIndexOfAddress);
            final String _tmpPic;
            _tmpPic = _cursor.getString(_cursorIndexOfPic);
            final double _tmpPrice;
            _tmpPrice = _cursor.getDouble(_cursorIndexOfPrice);
            final double _tmpScore;
            _tmpScore = _cursor.getDouble(_cursorIndexOfScore);
            final String _tmpDistance;
            _tmpDistance = _cursor.getString(_cursorIndexOfDistance);
            final String _tmpDuration;
            _tmpDuration = _cursor.getString(_cursorIndexOfDuration);
            final int _tmpBed;
            _tmpBed = _cursor.getInt(_cursorIndexOfBed);
            final String _tmpDateTour;
            _tmpDateTour = _cursor.getString(_cursorIndexOfDateTour);
            final String _tmpTimeTour;
            _tmpTimeTour = _cursor.getString(_cursorIndexOfTimeTour);
            final String _tmpDescription;
            _tmpDescription = _cursor.getString(_cursorIndexOfDescription);
            final String _tmpTourGuideName;
            _tmpTourGuideName = _cursor.getString(_cursorIndexOfTourGuideName);
            final String _tmpTourGuidePhone;
            _tmpTourGuidePhone = _cursor.getString(_cursorIndexOfTourGuidePhone);
            final String _tmpTourGuidePic;
            _tmpTourGuidePic = _cursor.getString(_cursorIndexOfTourGuidePic);
            _result = new FavoriteEntity(_tmpTitle,_tmpAddress,_tmpPic,_tmpPrice,_tmpScore,_tmpDistance,_tmpDuration,_tmpBed,_tmpDateTour,_tmpTimeTour,_tmpDescription,_tmpTourGuideName,_tmpTourGuidePhone,_tmpTourGuidePic);
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
  public Object isFavorite(final String title, final Continuation<? super Boolean> $completion) {
    final String _sql = "SELECT EXISTS(SELECT 1 FROM favorites WHERE title = ?)";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 1);
    int _argIndex = 1;
    _statement.bindString(_argIndex, title);
    final CancellationSignal _cancellationSignal = DBUtil.createCancellationSignal();
    return CoroutinesRoom.execute(__db, false, _cancellationSignal, new Callable<Boolean>() {
      @Override
      @NonNull
      public Boolean call() throws Exception {
        final Cursor _cursor = DBUtil.query(__db, _statement, false, null);
        try {
          final Boolean _result;
          if (_cursor.moveToFirst()) {
            final int _tmp;
            _tmp = _cursor.getInt(0);
            _result = _tmp != 0;
          } else {
            _result = false;
          }
          return _result;
        } finally {
          _cursor.close();
          _statement.release();
        }
      }
    }, $completion);
  }

  @NonNull
  public static List<Class<?>> getRequiredConverters() {
    return Collections.emptyList();
  }
}
