package com.buixuantu.parking.ServiceImpl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.buixuantu.parking.Repository.PositionsRepository;
import com.buixuantu.parking.Service.PositionsService;
import com.buixuantu.parking.entity.PositionsEntity;

@Service
public class PositionsServiceImpl implements PositionsService{
		@Autowired
		private PositionsRepository positionsRepository;
		
		@Override
		public List<PositionsEntity> getAllPositions() {
			return positionsRepository.findAll();
		}

		@Override
		public PositionsEntity findPositionById(String id) {
			return positionsRepository.findById(id).orElse(null);
		}

		@Override
		public void updatePositionById(String id, boolean a) {
			PositionsEntity tmp = positionsRepository.findById(id).orElse(null);
			tmp.setStatus(a);
			positionsRepository.save(tmp);
		}

		@Override
		public PositionsEntity addPosition(String id,boolean status) {
			PositionsEntity position = new PositionsEntity(id,status);
			return positionsRepository.save(position);
		}

		@Override
		public void deletePositionById(String id) {
			if(positionsRepository.findById(id).orElse(null).isStatus()==false) {
				positionsRepository.deleteById(id);
			}
		}
		
		@Override
		public boolean checkPosition(String id) {
			if(positionsRepository.findById(id).orElse(null).isStatus()==true) 
			{
				return false;
			}
			return true;
		}

	@Override
	public Page<PositionsEntity> findAllPage(Pageable pageable) {
		return positionsRepository.findAll(pageable);
	}

}