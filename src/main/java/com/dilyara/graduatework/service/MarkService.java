package com.dilyara.graduatework.service;

import com.dilyara.graduatework.entity.Mark;
import com.dilyara.graduatework.entity.User;
import com.dilyara.graduatework.exception.ForbiddenException;
import com.dilyara.graduatework.exception.NotFoundException;
import com.dilyara.graduatework.repo.MarkRepo;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.List;

@Service
@RequiredArgsConstructor
public class MarkService {
  private final MarkRepo markRepo;

  public Mark findById (User user, Long id) {
    Mark mark = findById(id);

    if (mark.getUser().equals(user)) {
      return mark;
    }

    throw new ForbiddenException();
  }

  public Mark findById (Long id) {
    return markRepo.findById(id).orElseThrow(NotFoundException::new);
  }

  public List<Mark> findAll (User user, String index) {
    return markRepo.findAll(user, LocalDate.now().withDayOfMonth(1).plus(Integer.parseInt(index), ChronoUnit.MONTHS));
  }

  public Mark create (User user, Mark mark) {
    mark.setUser(user);
    return markRepo.save(mark);
  }

  public Mark update (User user, Long id, Mark mark) {
    Mark markFromDb = findById(user, id);
    BeanUtils.copyProperties(mark, markFromDb, "id");
    return create(user, markFromDb);
  }

  public Boolean delete (User user, Long id) {
    Mark mark = findById(user, id);
    markRepo.delete(mark);
    return true;
  }
}
