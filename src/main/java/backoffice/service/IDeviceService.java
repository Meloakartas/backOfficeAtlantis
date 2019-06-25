package backoffice.service;

import backoffice.model.Device;
import backoffice.model.User;

import java.util.List;

public interface IDeviceService {

    List<Device> findAll();

    Device findDeviceById(long id);

    Device updateOrAddDevice(Device device);
}
