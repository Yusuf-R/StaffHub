#!/usr/bin/env bash

set -e

ROOT="src/main/java/com/naviroq/staffhub"

echo "Creating package structure..."

# ===========================
# COMMON
# ===========================
mkdir -p "$ROOT/common/entity"
mkdir -p "$ROOT/common/enums"
mkdir -p "$ROOT/common/exception"
mkdir -p "$ROOT/common/response"
mkdir -p "$ROOT/common/mapper"
mkdir -p "$ROOT/common/util"
mkdir -p "$ROOT/common/validation"
mkdir -p "$ROOT/common/constants"

# ===========================
# CONFIG
# ===========================
mkdir -p "$ROOT/config"

# ===========================
# ORGANIZATION
# ===========================
mkdir -p "$ROOT/organization/controller"
mkdir -p "$ROOT/organization/service"
mkdir -p "$ROOT/organization/repository"
mkdir -p "$ROOT/organization/entity"
mkdir -p "$ROOT/organization/mapper"
mkdir -p "$ROOT/organization/specification"
mkdir -p "$ROOT/organization/event"

mkdir -p "$ROOT/organization/dto/employee"
mkdir -p "$ROOT/organization/dto/department"
mkdir -p "$ROOT/organization/dto/position"
mkdir -p "$ROOT/organization/dto/employment"

# ===========================
# IDENTITY
# ===========================
mkdir -p "$ROOT/identity/controller"
mkdir -p "$ROOT/identity/service"
mkdir -p "$ROOT/identity/repository"
mkdir -p "$ROOT/identity/entity"
mkdir -p "$ROOT/identity/dto"
mkdir -p "$ROOT/identity/mapper"
mkdir -p "$ROOT/identity/security"

# ===========================
# HR
# ===========================
mkdir -p "$ROOT/hr/controller"
mkdir -p "$ROOT/hr/service"
mkdir -p "$ROOT/hr/repository"
mkdir -p "$ROOT/hr/entity"
mkdir -p "$ROOT/hr/dto"
mkdir -p "$ROOT/hr/mapper"

# ===========================
# ENGAGEMENT
# ===========================
mkdir -p "$ROOT/engagement/controller"
mkdir -p "$ROOT/engagement/service"
mkdir -p "$ROOT/engagement/repository"
mkdir -p "$ROOT/engagement/entity"
mkdir -p "$ROOT/engagement/dto"
mkdir -p "$ROOT/engagement/mapper"

# ===========================
# PLATFORM
# ===========================
mkdir -p "$ROOT/platform/controller"
mkdir -p "$ROOT/platform/service"
mkdir -p "$ROOT/platform/repository"
mkdir -p "$ROOT/platform/entity"
mkdir -p "$ROOT/platform/dto"

echo "Creating Java files..."

# ===========================
# CONFIG
# ===========================
touch "$ROOT/config/SecurityConfig.java"
touch "$ROOT/config/JwtConfig.java"
touch "$ROOT/config/OpenApiConfig.java"

# ===========================
# COMMON
# ===========================
touch "$ROOT/common/entity/BaseEntity.java"

touch "$ROOT/common/enums/Gender.java"
touch "$ROOT/common/enums/EmploymentStatus.java"
touch "$ROOT/common/enums/EmploymentType.java"
touch "$ROOT/common/enums/LeaveStatus.java"
touch "$ROOT/common/enums/RoleType.java"
touch "$ROOT/common/enums/EventCategory.java"
touch "$ROOT/common/enums/NotificationType.java"

# ===========================
# ORGANIZATION
# ===========================
touch "$ROOT/organization/entity/Employee.java"
touch "$ROOT/organization/entity/Department.java"
touch "$ROOT/organization/entity/Position.java"
touch "$ROOT/organization/entity/Employment.java"

touch "$ROOT/organization/repository/EmployeeRepository.java"
touch "$ROOT/organization/repository/DepartmentRepository.java"
touch "$ROOT/organization/repository/PositionRepository.java"
touch "$ROOT/organization/repository/EmploymentRepository.java"

touch "$ROOT/organization/service/EmployeeService.java"
touch "$ROOT/organization/service/DepartmentService.java"
touch "$ROOT/organization/service/PositionService.java"
touch "$ROOT/organization/service/EmploymentService.java"

touch "$ROOT/organization/controller/EmployeeController.java"
touch "$ROOT/organization/controller/DepartmentController.java"
touch "$ROOT/organization/controller/PositionController.java"
touch "$ROOT/organization/controller/EmploymentController.java"

touch "$ROOT/organization/mapper/EmployeeMapper.java"
touch "$ROOT/organization/mapper/DepartmentMapper.java"
touch "$ROOT/organization/mapper/PositionMapper.java"
touch "$ROOT/organization/mapper/EmploymentMapper.java"

touch "$ROOT/organization/event/EmployeeCreatedEvent.java"
touch "$ROOT/organization/event/EmployeeTransferredEvent.java"
touch "$ROOT/organization/event/EmployeePromotedEvent.java"
touch "$ROOT/organization/event/EmploymentEndedEvent.java"

touch "$ROOT/organization/dto/employee/CreateEmployeeRequest.java"
touch "$ROOT/organization/dto/employee/UpdateEmployeeRequest.java"
touch "$ROOT/organization/dto/employee/EmployeeResponse.java"
touch "$ROOT/organization/dto/employee/EmployeeSummaryResponse.java"

touch "$ROOT/organization/dto/department/CreateDepartmentRequest.java"
touch "$ROOT/organization/dto/department/DepartmentResponse.java"

touch "$ROOT/organization/dto/position/CreatePositionRequest.java"
touch "$ROOT/organization/dto/position/PositionResponse.java"

touch "$ROOT/organization/dto/employment/CreateEmploymentRequest.java"
touch "$ROOT/organization/dto/employment/EmploymentResponse.java"

# ===========================
# IDENTITY
# ===========================
touch "$ROOT/identity/entity/User.java"
touch "$ROOT/identity/entity/Role.java"
touch "$ROOT/identity/entity/Permission.java"

touch "$ROOT/identity/repository/UserRepository.java"
touch "$ROOT/identity/repository/RoleRepository.java"
touch "$ROOT/identity/repository/PermissionRepository.java"

touch "$ROOT/identity/service/AuthService.java"
touch "$ROOT/identity/service/UserService.java"

touch "$ROOT/identity/controller/AuthController.java"

touch "$ROOT/identity/security/JwtService.java"
touch "$ROOT/identity/security/JwtFilter.java"
touch "$ROOT/identity/security/CustomUserDetails.java"
touch "$ROOT/identity/security/CustomUserDetailsService.java"

# ===========================
# HR
# ===========================
touch "$ROOT/hr/entity/LeaveType.java"
touch "$ROOT/hr/entity/LeaveBalance.java"
touch "$ROOT/hr/entity/LeaveRequest.java"
touch "$ROOT/hr/entity/ProfileUpdateRequest.java"

touch "$ROOT/hr/repository/LeaveTypeRepository.java"
touch "$ROOT/hr/repository/LeaveBalanceRepository.java"
touch "$ROOT/hr/repository/LeaveRequestRepository.java"
touch "$ROOT/hr/repository/ProfileUpdateRequestRepository.java"

touch "$ROOT/hr/service/LeaveService.java"

touch "$ROOT/hr/controller/LeaveController.java"

# ===========================
# ENGAGEMENT
# ===========================
touch "$ROOT/engagement/entity/Announcement.java"
touch "$ROOT/engagement/entity/AnnouncementRead.java"
touch "$ROOT/engagement/entity/Event.java"
touch "$ROOT/engagement/entity/Gallery.java"
touch "$ROOT/engagement/entity/Photo.java"
touch "$ROOT/engagement/entity/PhotoTag.java"

touch "$ROOT/engagement/repository/AnnouncementRepository.java"
touch "$ROOT/engagement/repository/EventRepository.java"
touch "$ROOT/engagement/repository/GalleryRepository.java"
touch "$ROOT/engagement/repository/PhotoRepository.java"

touch "$ROOT/engagement/service/AnnouncementService.java"
touch "$ROOT/engagement/service/EventService.java"

touch "$ROOT/engagement/controller/AnnouncementController.java"
touch "$ROOT/engagement/controller/EventController.java"

# ===========================
# PLATFORM
# ===========================
touch "$ROOT/platform/entity/AuditLog.java"
touch "$ROOT/platform/entity/Notification.java"
touch "$ROOT/platform/entity/Attachment.java"

touch "$ROOT/platform/repository/AuditLogRepository.java"
touch "$ROOT/platform/repository/NotificationRepository.java"
touch "$ROOT/platform/repository/AttachmentRepository.java"

touch "$ROOT/platform/service/AuditService.java"
touch "$ROOT/platform/service/NotificationService.java"
touch "$ROOT/platform/service/AttachmentService.java"

touch "$ROOT/platform/controller/NotificationController.java"

echo ""
echo "===================================="
echo "✅ StaffHub scaffold created!"
echo "===================================="