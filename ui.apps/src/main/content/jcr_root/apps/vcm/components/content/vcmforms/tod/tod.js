/**
 * Common Form Functions
 * Shared functionality across all forms
 */

// Initialize all signature pads on the page
function initializeAllSignaturePads() {
    // Ensure signaturePads registry exists
    if (!window.signaturePads) {
        window.signaturePads = {};
    }

    const signatureCanvases = document.querySelectorAll('.signature-pad');
    signatureCanvases.forEach(canvas => {
        if (!window.signaturePads[canvas.id]) {
            // Get dimensions from attributes or use defaults
            const width = parseInt(canvas.getAttribute('width')) || canvas.offsetWidth || 400;
            const height = parseInt(canvas.getAttribute('height')) || 150;

            initializeSignaturePad(canvas.id, {
                width: width,
                height: height
            });
        }
    });

    // Setup conditional field visibility
    setupConditionalFields();
}

// Clear specific signature pad
function clearSignature(canvasId) {
    const signaturePad = window.signaturePads[canvasId];
    if (signaturePad) {
        signaturePad.clear();
    }
}

// Get signature data
function getSignatureData(canvasId, format = 'png', quality = 1.0) {
    const signaturePad = window.signaturePads[canvasId];
    return signaturePad ? signaturePad.getSignatureData(format, quality) : null;
}

// Collect all form data including signatures
function collectAllFormData() {
    const formData = {};
    const form = document.querySelector('form');

    if (form) {
        const formDataObj = new FormData(form);
        for (let [key, value] of formDataObj.entries()) {
            formData[key] = value;
        }
    }

    // Add signature data
    Object.keys(window.signaturePads || {}).forEach(canvasId => {
        const signatureData = getSignatureData(canvasId);
        if (signatureData) {
            formData[canvasId] = signatureData;
        }
    });

    return formData;
}

// Save form draft
function saveFormDraft() {
    const formData = collectAllFormData();
    const formType = document.querySelector('form').id || 'form';
    localStorage.setItem(`${formType}_draft`, JSON.stringify(formData));
    showFormMessage('Draft saved successfully', 'success');
}

// Submit form
function submitForm(event, formType) {
    event.preventDefault();

    if (!validateEntireForm()) {
        showFormMessage('Please fix validation errors before submitting', 'error');
        return;
    }

    const formData = collectAllFormData();
    console.log('Submitting form:', formType, formData);
    showFormMessage('Form submitted successfully', 'success');
}

// Validate entire form
function validateEntireForm() {
    const form = document.querySelector('form');
    if (!form) return true;

    const requiredFields = form.querySelectorAll('[required]');
    let isValid = true;

    requiredFields.forEach(field => {
        if (!field.value.trim()) {
            field.classList.add('error');
            isValid = false;
        } else {
            field.classList.remove('error');
        }
    });

    return isValid;
}

// Show form message
function showFormMessage(message, type) {
    // Remove existing messages
    const existingMessages = document.querySelectorAll('.form-message');
    existingMessages.forEach(msg => msg.remove());

    // Create new message
    const messageDiv = document.createElement('div');
    messageDiv.className = `form-message ${type}`;
    messageDiv.textContent = message;
    messageDiv.style.cssText = `
        position: fixed;
        top: 20px;
        right: 20px;
        padding: 12px 20px;
        border-radius: 4px;
        color: white;
        font-weight: 500;
        z-index: 1000;
        background-color: ${type === 'success' ? 'var(--accent-green)' : 'var(--error-red)'};
    `;

    document.body.appendChild(messageDiv);

    // Auto remove after 3 seconds
    setTimeout(() => {
        messageDiv.remove();
    }, 3000);
}

// Add dynamic section
function addDynamicSection(sectionType) {
    // Handle different section type formats
    let containerId;
    if (sectionType === 'primaryBeneficiary') {
        containerId = 'primaryBeneficiaries';
    } else if (sectionType === 'secondaryBeneficiary') {
        containerId = 'secondaryBeneficiaries';
    } else {
        containerId = `${sectionType}s`;
    }

    const container = document.getElementById(containerId);
    if (!container) {
        console.error(`Container not found: ${containerId}`);
        return;
    }

    const existingEntries = container.querySelectorAll('.beneficiary-entry');
    const newIndex = existingEntries.length + 1;

    const template = existingEntries[0];
    if (!template) {
        console.error('No template found for dynamic section');
        return;
    }

    const newEntry = template.cloneNode(true);

    // Update IDs and names
    const inputs = newEntry.querySelectorAll('input');
    inputs.forEach(input => {
        const oldId = input.id;
        const oldName = input.name;

        if (oldId) {
            // Replace the number in the ID
            const newId = oldId.replace(/(\d+)/, newIndex);
            input.id = newId;

            // Update corresponding label
            const label = newEntry.querySelector(`label[for="${oldId}"]`);
            if (label) {
                label.setAttribute('for', newId);
            }
        }

        if (oldName) {
            // Replace the number in the name
            input.name = oldName.replace(/(\d+)/, newIndex);
        }

        // Clear the value
        input.value = '';
    });

    // Update data attributes
    newEntry.setAttribute('data-beneficiary-index', newIndex);

    // Add remove button if this isn't the first entry
    if (newIndex > 1) {
        let formActions = newEntry.querySelector('.form-actions');
        if (!formActions) {
            formActions = document.createElement('div');
            formActions.className = 'form-actions';
            newEntry.appendChild(formActions);
        }

        // Clear existing buttons
        formActions.innerHTML = '';

        const removeBtn = document.createElement('button');
        removeBtn.type = 'button';
        removeBtn.className = 'btn btn-secondary';
        removeBtn.textContent = 'Remove';
        removeBtn.onclick = () => removeDynamicSection(newEntry);
        formActions.appendChild(removeBtn);
    }

    container.appendChild(newEntry);
    console.log(`Added ${sectionType} #${newIndex}`);
}

// Remove dynamic section
function removeDynamicSection(sectionElement) {
    if (sectionElement && sectionElement.parentNode) {
        const container = sectionElement.parentNode;
        const remainingEntries = container.querySelectorAll('.beneficiary-entry');

        // Don't remove if it's the last entry
        if (remainingEntries.length <= 1) {
            console.log('Cannot remove the last beneficiary entry');
            return;
        }

        sectionElement.remove();
        console.log('Removed beneficiary entry');
    }
}

// Toggle conditional visibility
function toggleConditionalVisibility(triggerElement, targetSelector, showCondition) {
    const target = document.querySelector(targetSelector);
    if (!target) return;

    const shouldShow = typeof showCondition === 'function' ? showCondition(triggerElement) : showCondition;
    target.style.display = shouldShow ? 'block' : 'none';
}

// Setup conditional field visibility
function setupConditionalFields() {
    // Joint account holder signature visibility
    const jointFields = ['jointFirstName', 'jointMiddleInitial', 'jointLastName'];
    const jointSignatureGroup = document.querySelector('[for="jointAccountHolderSignature"]')?.closest('.form-group');

    if (jointSignatureGroup) {
        function checkJointFields() {
            const hasJointData = jointFields.some(fieldId => {
                const field = document.getElementById(fieldId);
                return field && field.value.trim() !== '';
            });

            jointSignatureGroup.style.display = hasJointData ? 'block' : 'none';

            const dateField = document.getElementById('jointAccountHolderSignatureDate');
            if (dateField) {
                dateField.required = hasJointData;
            }
        }

        jointFields.forEach(fieldId => {
            const field = document.getElementById(fieldId);
            if (field) {
                field.addEventListener('input', checkJointFields);
            }
        });

        // Initial check
        checkJointFields();
    }
}

/**
 * High-Resolution Signature Pad Component
 * Reusable signature capture with high DPI support
 */
class HighResSignaturePad {
    constructor(canvasId, options = {}) {
        this.canvasId = canvasId;
        this.canvas = document.getElementById(canvasId);
        this.ctx = this.canvas.getContext('2d');
        this.isDrawing = false;
        this.isEmpty = true;
        this.lastX = 0;
        this.lastY = 0;

        // Default options
        this.options = {
            width: options.width || 400,
            height: options.height || 150,
            lineWidth: options.lineWidth || 2,
            strokeStyle: options.strokeStyle || '#000000',
            backgroundColor: options.backgroundColor || '#ffffff',
            penColor: options.penColor || '#000000',
            velocityFilterWeight: options.velocityFilterWeight || 0.7,
            minWidth: options.minWidth || 0.5,
            maxWidth: options.maxWidth || 2.5,
            throttle: options.throttle || 16, // 60fps
            minDistance: options.minDistance || 5,
            dotSize: options.dotSize || 1.5
        };

        this.points = [];
        this.lastTimestamp = 0;
        this.lastVelocity = 0;

        this.init();
    }

    init() {
        this.setupCanvas();
        this.bindEvents();
        this.clear();
    }

    setupCanvas() {
        // Set up high DPI canvas
        const ratio = Math.max(window.devicePixelRatio || 1, 1);
        this.canvas.width = this.options.width * ratio;
        this.canvas.height = this.options.height * ratio;
        this.canvas.style.width = this.options.width + 'px';
        this.canvas.style.height = this.options.height + 'px';

        // Scale context for high DPI
        this.ctx.scale(ratio, ratio);

        // Set canvas properties
        this.ctx.lineCap = 'round';
        this.ctx.lineJoin = 'round';
        this.ctx.fillStyle = this.options.backgroundColor;
        this.ctx.strokeStyle = this.options.strokeStyle;

        // Add CSS classes
        this.canvas.classList.add('signature-pad');
        if (!this.canvas.style.border) {
            this.canvas.style.border = '2px solid var(--muted-steel-blue, #ccc)';
            this.canvas.style.borderRadius = '4px';
            this.canvas.style.cursor = 'crosshair';
            this.canvas.style.background = this.options.backgroundColor;
        }
    }

    bindEvents() {
        // Mouse events
        this.canvas.addEventListener('mousedown', this.handleMouseDown.bind(this));
        this.canvas.addEventListener('mousemove', this.handleMouseMove.bind(this));
        this.canvas.addEventListener('mouseup', this.handleMouseUp.bind(this));
        this.canvas.addEventListener('mouseleave', this.handleMouseUp.bind(this));

        // Touch events for mobile
        this.canvas.addEventListener('touchstart', this.handleTouchStart.bind(this));
        this.canvas.addEventListener('touchmove', this.handleTouchMove.bind(this));
        this.canvas.addEventListener('touchend', this.handleTouchEnd.bind(this));

        // Prevent scrolling on touch
        this.canvas.addEventListener('touchstart', (e) => e.preventDefault());
        this.canvas.addEventListener('touchmove', (e) => e.preventDefault());
    }

    getPointFromEvent(event) {
        const rect = this.canvas.getBoundingClientRect();
        const scaleX = this.canvas.width / rect.width;
        const scaleY = this.canvas.height / rect.height;

        return {
            x: (event.clientX - rect.left) * scaleX / (window.devicePixelRatio || 1),
            y: (event.clientY - rect.top) * scaleY / (window.devicePixelRatio || 1),
            time: Date.now()
        };
    }

    handleMouseDown(event) {
        this.isDrawing = true;
        const point = this.getPointFromEvent(event);
        this.addPoint(point);
        this.isEmpty = false;
    }

    handleMouseMove(event) {
        if (!this.isDrawing) return;

        const point = this.getPointFromEvent(event);
        this.addPoint(point);
    }

    handleMouseUp() {
        if (this.isDrawing) {
            this.isDrawing = false;
            this.addPoint(null); // End stroke
        }
    }

    handleTouchStart(event) {
        event.preventDefault();
        const touch = event.touches[0];
        const mouseEvent = new MouseEvent('mousedown', {
            clientX: touch.clientX,
            clientY: touch.clientY
        });
        this.canvas.dispatchEvent(mouseEvent);
    }

    handleTouchMove(event) {
        event.preventDefault();
        const touch = event.touches[0];
        const mouseEvent = new MouseEvent('mousemove', {
            clientX: touch.clientX,
            clientY: touch.clientY
        });
        this.canvas.dispatchEvent(mouseEvent);
    }

    handleTouchEnd(event) {
        event.preventDefault();
        const mouseEvent = new MouseEvent('mouseup', {});
        this.canvas.dispatchEvent(mouseEvent);
    }

    addPoint(point) {
        if (!point) {
            // End of stroke
            this.points.push(null);
            return;
        }

        const now = Date.now();
        if (now - this.lastTimestamp < this.options.throttle) {
            return;
        }
        this.lastTimestamp = now;

        if (this.points.length > 0 && this.points[this.points.length - 1]) {
            const lastPoint = this.points[this.points.length - 1];
            const distance = Math.sqrt(
                Math.pow(point.x - lastPoint.x, 2) + Math.pow(point.y - lastPoint.y, 2)
            );

            if (distance < this.options.minDistance) {
                return;
            }

            // Calculate velocity for variable line width
            const timeDelta = point.time - lastPoint.time;
            const velocity = distance / timeDelta;

            // Smooth velocity
            this.lastVelocity = this.options.velocityFilterWeight * velocity +
                (1 - this.options.velocityFilterWeight) * this.lastVelocity;

            // Calculate line width based on velocity
            const lineWidth = Math.max(
                this.options.minWidth,
                Math.min(this.options.maxWidth, this.options.maxWidth / this.lastVelocity * 0.5)
            );

            this.drawLine(lastPoint, point, lineWidth);
        } else {
            // First point - draw a dot
            this.drawDot(point);
        }

        this.points.push(point);
    }

    drawLine(from, to, lineWidth) {
        this.ctx.lineWidth = lineWidth;
        this.ctx.strokeStyle = this.options.penColor;
        this.ctx.beginPath();
        this.ctx.moveTo(from.x, from.y);
        this.ctx.lineTo(to.x, to.y);
        this.ctx.stroke();
    }

    drawDot(point) {
        this.ctx.fillStyle = this.options.penColor;
        this.ctx.beginPath();
        this.ctx.arc(point.x, point.y, this.options.dotSize, 0, 2 * Math.PI);
        this.ctx.fill();
    }

    clear() {
        this.ctx.fillStyle = this.options.backgroundColor;
        this.ctx.fillRect(0, 0, this.canvas.width, this.canvas.height);
        this.points = [];
        this.isEmpty = true;
        this.lastVelocity = 0;
    }

    getSignatureData(format = 'png', quality = 1.0) {
        if (this.isEmpty) {
            return null;
        }

        // Create a new canvas with white background for export
        const exportCanvas = document.createElement('canvas');
        const exportCtx = exportCanvas.getContext('2d');

        exportCanvas.width = this.canvas.width;
        exportCanvas.height = this.canvas.height;

        // Fill with white background
        exportCtx.fillStyle = '#ffffff';
        exportCtx.fillRect(0, 0, exportCanvas.width, exportCanvas.height);

        // Draw the signature
        exportCtx.drawImage(this.canvas, 0, 0);

        return exportCanvas.toDataURL(`image/${format}`, quality);
    }

    loadSignature(dataURL) {
        const img = new Image();
        img.onload = () => {
            this.clear();
            this.ctx.drawImage(img, 0, 0);
            this.isEmpty = false;
        };
        img.src = dataURL;
    }

    resize(width, height) {
        const imageData = this.getSignatureData();
        this.options.width = width;
        this.options.height = height;
        this.setupCanvas();
        if (imageData) {
            this.loadSignature(imageData);
        }
    }

    // Static method to create signature pad with controls
    static createWithControls(containerId, canvasId, options = {}) {
        const container = document.getElementById(containerId);
        if (!container) {
            throw new Error(`Container with id '${containerId}' not found`);
        }

        // Create canvas if it doesn't exist
        let canvas = document.getElementById(canvasId);
        if (!canvas) {
            canvas = document.createElement('canvas');
            canvas.id = canvasId;
            container.appendChild(canvas);
        }

        // Create signature pad
        const signaturePad = new HighResSignaturePad(canvasId, options);

        // Create controls
        const controlsDiv = document.createElement('div');
        controlsDiv.className = 'signature-controls';
        controlsDiv.style.marginTop = '10px';
        controlsDiv.style.display = 'flex';
        controlsDiv.style.gap = '10px';

        const clearBtn = document.createElement('button');
        clearBtn.type = 'button';
        clearBtn.className = 'btn btn-secondary';
        clearBtn.textContent = 'Clear Signature';
        clearBtn.onclick = () => signaturePad.clear();

        const undoBtn = document.createElement('button');
        undoBtn.type = 'button';
        undoBtn.className = 'btn btn-secondary';
        undoBtn.textContent = 'Undo';
        undoBtn.onclick = () => signaturePad.undo();

        controlsDiv.appendChild(clearBtn);
        controlsDiv.appendChild(undoBtn);
        container.appendChild(controlsDiv);

        return signaturePad;
    }

    undo() {
        // Find the last null (end of stroke) and remove points after it
        let lastStrokeEnd = -1;
        for (let i = this.points.length - 1; i >= 0; i--) {
            if (this.points[i] === null) {
                lastStrokeEnd = i;
                break;
            }
        }

        if (lastStrokeEnd >= 0) {
            this.points = this.points.slice(0, lastStrokeEnd);
        } else {
            this.points = [];
        }

        this.redraw();
    }

    redraw() {
        this.clear();

        if (this.points.length === 0) {
            return;
        }

        let currentStroke = [];

        for (const point of this.points) {
            if (point === null) {
                // End of stroke
                if (currentStroke.length > 0) {
                    this.drawStroke(currentStroke);
                    currentStroke = [];
                }
            } else {
                currentStroke.push(point);
            }
        }

        // Draw final stroke if exists
        if (currentStroke.length > 0) {
            this.drawStroke(currentStroke);
        }

        this.isEmpty = this.points.length === 0;
    }

    drawStroke(points) {
        if (points.length === 0) return;

        if (points.length === 1) {
            this.drawDot(points[0]);
            return;
        }

        for (let i = 1; i < points.length; i++) {
            this.drawLine(points[i - 1], points[i], this.options.lineWidth);
        }
    }
}

// Global signature pad registry
window.signaturePads = window.signaturePads || {};

// Convenience functions for backward compatibility
function initializeSignaturePad(canvasId, options = {}) {
    const signaturePad = new HighResSignaturePad(canvasId, options);
    window.signaturePads[canvasId] = signaturePad;
    return signaturePad;
}

function clearSignature(canvasId) {
    const signaturePad = window.signaturePads[canvasId];
    if (signaturePad) {
        signaturePad.clear();
    }
}

function getSignatureData(canvasId, format = 'png', quality = 1.0) {
    const signaturePad = window.signaturePads[canvasId];
    return signaturePad ? signaturePad.getSignatureData(format, quality) : null;
}

function isSignatureEmpty(canvasId) {
    const signaturePad = window.signaturePads[canvasId];
    return signaturePad ? signaturePad.isEmpty : true;
}

// Export for module systems
if (typeof module !== 'undefined' && module.exports) {
    module.exports = HighResSignaturePad;
}


// ===== Utility Helpers =====
function clearErrors() {
    document.querySelectorAll(".error-highlight").forEach(el => el.classList.remove("error-highlight"));
    document.querySelectorAll(".tod-error-message").forEach(msg => msg.remove());
}

function addError(element, message) {
    if (!element) return;
    element.classList.add("error-highlight");

    // Prevent duplicate messages
    if (element.parentNode.querySelector(".tod-error-message")) return;

    const error = document.createElement("div");
    error.className = "tod-error-message";
    error.textContent = message;
    element.parentNode.appendChild(error);
}

function scrollToFirstError() {
    const firstError = document.querySelector(".error-highlight");
    if (firstError) {
        firstError.scrollIntoView({ behavior: "smooth", block: "center" });
        firstError.focus();
    }
}

// ===== Validation Sections =====

// Personal Info Validation
function validatePersonalInfo() {
    let valid = true;
    const requiredFields = [
        { id: "memberNumber", label: "Member Number" },
        { id: "socialSecurityNumber", label: "Social Security Number" },
        { id: "firstName", label: "First Name" },
        { id: "middleInitial", label: "Middle Initial" },
        { id: "lastName", label: "Last Name" },
        { id: "physicalAddress", label: "Address Line 1 is mandatory. It cannot be left blank." },
        { id: "city", label: "City" },
        { id: "state", label: "State" },
        { id: "zipCode", label: "Zip Code" },
        { id: "residencePhone", label: "Enter Residence  phone number" },

        { id: "jointFirstName", label: "First Name" },
        { id: "jointMiddleInitial", label: "Middle Initial" },
        { id: "jointLastName", label: "Last Name" },
    ];

    requiredFields.forEach(({ id, label }) => {
        const el = document.getElementById(id);
        if (!el.value.trim()) {
            addError(el, `${label} is required`);
            valid = false;
        }
    });

    // SSN validation pattern
    const ssn = document.getElementById("socialSecurityNumber");
    const ssnPattern = /^[0-9]{3}-?[0-9]{2}-?[0-9]{4}$/;
    if (ssn.value.trim() && !ssnPattern.test(ssn.value.trim())) {
        addError(ssn, "Please enter a valid social security or tax ID number.(e.g. 123-45-6789)");
        valid = false;
    }

    return valid;
}

// Account Servicing Validation
function validateAccountServicing() {
    let valid = true;
    const accountType = document.querySelector('input[name="accountType"]:checked');
    const serviceType = document.querySelector('input[name="accountServicing"]:checked');

    if (!accountType) {
        document.querySelectorAll('input[name="accountType"]').forEach(r => addError(r, "Select account type"));
        valid = false;
    }
    if (!serviceType) {
        document.querySelectorAll('input[name="accountServicing"]').forEach(r => addError(r, "Here user should select atleast one option"));
        valid = false;
    }
    return valid;
}

// Beneficiaries (Primary/Secondary)
function validateBeneficiaries(type) {
    let valid = true;
    const entries = document.querySelectorAll(`#${type}Beneficiaries [data-beneficiary-type="${type}"]`);
    let totalPercent = 0;

    entries.forEach(entry => {
        const name = entry.querySelectorAll("input")[0];
        const ssn = entry.querySelectorAll("input")[1];
        const dob = entry.querySelectorAll("input")[2];
        const relationship = entry.querySelectorAll("input")[3];
        const percent = entry.querySelectorAll("input")[4];

        const ssnPattern = /^[0-9]{3}-?[0-9]{2}-?[0-9]{4}$/;
        if (!ssn.value.trim() && !ssnPattern.test(ssn.value.trim())) {
            addError(ssn, "Please enter a valid social security or tax ID number.(e.g. 123-45-6789)");
            valid = false;
        }

        const anyFilled = false
        if (!anyFilled) {
            if (!name?.value.trim()) { addError(name, "Name required"); valid = false; }
            if (!relationship?.value.trim()) { addError(relationship, "Relationship required"); valid = false; }
            if (!percent?.value.trim()) { addError(percent, "Percentage required"); valid = false; }
            if (!dob?.value) { addError(dob, "Please enter the Date"); valid = false; }
            else totalPercent += parseFloat(percent.value);
        }
    });

    if (totalPercent > 0 && totalPercent !== 100) {
        alert(`${type} Distribution Percentages must total to 100%. Current total: ${totalPercent}%`);
        valid = false;
    }

    return valid;
}

// ===== Master Validation =====
function validateFormData() {
    clearErrors();

    let valid = true;
    if (!validatePersonalInfo()) valid = false;
    if (!validateAccountServicing()) valid = false;
    if (!validateBeneficiaries("primary")) valid = false;
    if (!validateBeneficiaries("secondary")) valid = false;

    if (!valid) scrollToFirstError();
    return valid;
}

document.addEventListener("DOMContentLoaded", function () {
    const submitBtn = document.getElementById("submitBtn");
    submitBtn.addEventListener("click", function (event) {
        event.preventDefault(); // prevents default form submission (optional)
        validateFormData(); // call your function
    });
});

document.addEventListener("DOMContentLoaded", function () {
    const primaryBtn = document.getElementById("primary-button");
    primaryBtn.addEventListener("click", function (event) {
        event.preventDefault(); // prevents default form submission (optional)
        addDynamicSection("primaryBeneficiary"); // call your function
    });
});

document.addEventListener("DOMContentLoaded", function () {
    const secondaryBtn = document.getElementById("secondary-button");
    secondaryBtn.addEventListener("click", function (event) {
        event.preventDefault(); // prevents default form submission (optional)
        addDynamicSection("secondaryBeneficiary"); // call your function
    });
});


    const removeError = (el, type) => {
        el?.classList.remove("error-highlight", `error-${type}`);
        el.parentElement.querySelector(".tod-error-message").innerHTML = "";
    }

    document.querySelectorAll("input").forEach(input => {
      input.addEventListener("input", () => {
        if (input.type === "text" && input.value.trim()) removeError(input, "text");
        if (input.type === "number" && parseFloat(input.value) > 0) removeError(input, "number");
        if (input.type === "date" && isPastDate(input.value)) removeError(input, "date");
      });

      if (input.type === "radio" || input.type === "checkbox") {
        input.addEventListener("change", () => {
          document.querySelectorAll(`input[name="${input.name}"]`).forEach(r => removeError(r, "radio"));
        });
      }
    });

document.querySelectorAll("input").forEach(input => {
  input.addEventListener("input", () => {
    if (input.type === "text" && input.value.trim()) removeError(input, "text");
    if (input.type === "number" && parseFloat(input.value) > 0) removeError(input, "number");
    if (input.type === "date" && isPastDate(input.value)) removeError(input, "date");
  });

  if (input.type === "radio" || input.type === "checkbox") {
    input.addEventListener("change", () => {
      document.querySelectorAll(`input[name="${input.name}"]`).forEach(r => removeError(r, "radio"));
    });
  }
});