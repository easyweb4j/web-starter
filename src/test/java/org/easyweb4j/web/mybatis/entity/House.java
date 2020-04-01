package org.easyweb4j.web.mybatis.entity;

import com.google.common.base.Objects;
import java.time.LocalDateTime;
import org.easyweb4j.web.core.dao.DeletedStatus;

public class House {

  private Integer id;
  private String name;
  private Integer floorNumber;
  private DeletedStatus state;
  private DeletedStatus stateTs;
  private LocalDateTime createDate;


  public Integer getId() {
    return id;
  }

  public void setId(Integer id) {
    this.id = id;
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public Integer getFloorNumber() {
    return floorNumber;
  }

  public void setFloorNumber(Integer floorNumber) {
    this.floorNumber = floorNumber;
  }

  public DeletedStatus getState() {
    return state;
  }

  public void setState(DeletedStatus state) {
    this.state = state;
  }

  public DeletedStatus getStateTs() {
    return stateTs;
  }

  public void setStateTs(DeletedStatus stateTs) {
    this.stateTs = stateTs;
  }

  public LocalDateTime getCreateDate() {
    return createDate;
  }

  public void setCreateDate(LocalDateTime createDate) {
    this.createDate = createDate;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    House house = (House) o;
    return Objects.equal(id, house.id) &&
      Objects.equal(name, house.name) &&
      Objects.equal(floorNumber, house.floorNumber) &&
      state == house.state &&
      stateTs == house.stateTs &&
      Objects.equal(createDate, house.createDate);
  }

  @Override
  public int hashCode() {
    return Objects.hashCode(id, name, floorNumber, state, stateTs, createDate);
  }
}
