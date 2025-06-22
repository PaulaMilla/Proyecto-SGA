import { ComponentFixture, TestBed } from '@angular/core/testing';

import { InventarioInfoComponent } from './inventario-info.component';

describe('InventarioInfoComponent', () => {
  let component: InventarioInfoComponent;
  let fixture: ComponentFixture<InventarioInfoComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [InventarioInfoComponent]
    })
    .compileComponents();

    fixture = TestBed.createComponent(InventarioInfoComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
