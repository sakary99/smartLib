package com.webcash.sws.comm.util;

import java.util.ArrayList;
import java.util.Collection;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

public class BizLRUCache<K, V>
{
  private static final float mHashTableLoadFactor = 0.75f;
  private LinkedHashMap<K, V> mMap;
  private int mCacheSize;

  public BizLRUCache(int cacheSize)
  {
    this.mCacheSize = cacheSize;
    int hashTableCapacity = (int) Math.ceil(cacheSize / mHashTableLoadFactor) + 1;
    mMap = new LinkedHashMap<K, V>(hashTableCapacity, mHashTableLoadFactor, true)
    {
      private static final long serialVersionUID = 1;

      @Override
      protected boolean removeEldestEntry(Map.Entry<K, V> eldest)
      {
        return size() > BizLRUCache.this.mCacheSize;
      }
    };
    
  }

  public synchronized boolean containKey(String key)
  {
    return mMap.containsKey(key);
  }
  
  public synchronized V get(K key)
  {
    return mMap.get(key);
  }

  public synchronized void put(K key, V value)
  {
    mMap.put(key, value);
  }

  public synchronized int usedEntries()
  {
    return mMap.size();
  }

  public synchronized Collection<Map.Entry<K, V>> getAll()
  {
    return new ArrayList<Map.Entry<K, V>>(mMap.entrySet());
  }
  
  
  public synchronized Set<Entry<K, V>> entrySet()
  {
    return mMap.entrySet();
  }
  
}
