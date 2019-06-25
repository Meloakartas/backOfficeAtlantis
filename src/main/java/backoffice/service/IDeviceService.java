package backoffice.service;

import backoffice.model.Device;

import java.util.List;

public interface IDeviceService {

    List<Device> findAll();

    Device findDeviceById(long id);
}
