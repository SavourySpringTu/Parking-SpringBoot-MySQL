package com.buixuantu.parking.Service;

import java.util.List;
import org.springframework.data.domain.Pageable;

import com.buixuantu.parking.entity.PositionsEntity;
import org.springframework.data.domain.Page;

public interface PositionsService {
	List<PositionsEntity> getAllPositions();
	PositionsEntity findPositionById(String id);
	void updatePositionById(String id, boolean a);
	PositionsEntity addPosition(String id,boolean status);
	void deletePositionById(String id);
	boolean checkPosition(String id);
	Page<PositionsEntity> findAllPage(Pageable pageable);
}
