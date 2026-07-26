package com.kibunmeshi.repository;

import com.kibunmeshi.domain.Food;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 料理（Food）データアクセス用マッパーインターフェース
 * 
 * MyBatisを使用してfoodsテーブルへのデータアクセスを提供する。
 * 気分とカテゴリに基づいた料理検索、ランダム推薦などの機能を提供する。
 * 
 * @author 作者名
 * @version 1.0.0
 * @see com.kibunmeshi.domain.Food
 */
@Mapper
public interface FoodMapper {
    
    /**
     * 指定された気分IDとカテゴリIDに該当する料理リストを取得する
     * 
     * このメソッドは主に推薦機能で使用され、ユーザーが選択した気分と
     * 料理カテゴリに基づいて候補となる料理を検索する。
     * 
     * SQL: SELECT ... FROM foods 
     *      WHERE emotion_id = #{emotionId} 
     *        AND category_id = #{categoryId}
     *        AND is_active = true
     * 
     * @param emotionId 気分ID（1:うれしい, 2:かなしい, 3:おこ, 4:つかれた, 5:わくわく, 6:おだやか）
     * @param categoryId カテゴリID（1:韓国料理, 2:洋食, 3:中華, 4:和食, 5:その他）
     * @return 条件に該当する料理のリスト。該当なしの場合は空リスト
     * @throws org.springframework.dao.DataAccessException データベースアクセス例外
     * 
     * @see #selectById(Integer)
     * @see #selectAll()
     */
    List<Food> findByEmotionAndCategory(
            @Param("emotionId") Integer emotionId, 
            @Param("categoryId") Integer categoryId);
    
    /**
     * 指定されたIDの料理を取得する
     * 
     * 料理詳細表示や、推薦履歴からの再表示などで使用される。
     * 該当IDが存在しない場合はnullを返す。
     * 
     * SQL: SELECT ... FROM foods WHERE id = #{id}
     * 
     * @param id 料理ID（1以上の整数）
     * @return 該当する料理エンティティ。存在しない場合はnull
     * @throws org.springframework.dao.DataAccessException データベースアクセス例外
     * 
     * @see #findByEmotionAndCategory(Integer, Integer)
     */
    Food selectById(@Param("id") Integer id);
    
    /**
     * 全ての料理を取得する
     * 
     * 管理機能での一覧表示や、データベースのバックアップなどで使用される。
     * 大量データの場合はメモリ使用量に注意が必要。
     * 
     * SQL: SELECT ... FROM foods ORDER BY id
     * 
     * @return 全料理のリスト。データがない場合は空リスト
     * @throws org.springframework.dao.DataAccessException データベースアクセス例外
     * 
     * @see #findByEmotionAndCategory(Integer, Integer)
     */
    List<Food> selectAll();

    /**
     * 料理を新規登録する（AI推薦など）
     *
     * @param food 登録する料理
     * @return 登録件数
     */
    int insert(Food food);
}
