package backoffice.service;

import backoffice.model.Device;
import backoffice.repository.DeviceRepository;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;
import java.util.List;

@Service
public class DeviceService implements IDeviceService {

    private final DeviceRepository repository;

    public DeviceService(DeviceRepository repository) {
        this.repository = repository;
    }

    @Override
    public List<Device> findAll() {
        return (List<Device>) repository.findAll();
    }

    @Override
    public Device findDeviceById(long id) {
        return repository.findById(id).orElseThrow(EntityNotFoundException::new);
    }

    @Override
    public Device updateOrAddDevice(Device device) {
        return repository.save(device);
    }
}
